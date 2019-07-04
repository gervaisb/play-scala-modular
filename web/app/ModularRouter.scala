import com.google.inject.{Injector, Key, TypeLiteral}
import javax.inject.Inject
import play.api.libs.typedmap.TypedMap
import play.api.mvc.request.{RemoteConnection, RequestTarget}
import play.api.mvc.{Action, Handler, Headers, RequestHeader, Results, WrappedRequest}
import play.api.routing.{Router, SimpleRouter}
import play.api.routing.Router.Routes
import play.api.{Configuration, Environment}
import play.core.routing.GeneratedRouter

import collection.JavaConverters._
import scala.util.{Failure, Success, Try}

case class ModuleRouter(router:Router, module:String) extends SimpleRouter {
  override def routes: Routes = {
    case request:RequestHeader => Action {
      Results.Ok("Greeting from my module")
    }
    case req => Action {
      Results.NotFound(s"No route found for $req")
    }
  }
}

class ModularRouter @Inject()(guice: Injector, environment: Environment, configuration: Configuration) extends Router {
  val generated = guice.getInstance(classOf[router.Routes]).asInstanceOf[Router]
  val root = Modules(guice, environment).routers
    .foldLeft(generated){(left, right) =>
      left.orElse(right._2.withPrefix(right._1))
    }

  override def routes: Routes = root.routes

  override def documentation: Seq[(String, String, String)] = root.documentation

  override def withPrefix(prefix: String): Router = root.withPrefix(prefix)

}

case class Modules(private val guice:Injector, environment: Environment) {
  def routers = routerKeys.map(key => {
    /* Naive implementation that assume that your router class is under a single
     * level package. */
    val prefix = environment.classLoader.loadClass(key.getTypeLiteral.getType.getTypeName).getPackage.getName
    val instance = guice.getInstance(key).asInstanceOf[Router]
    (prefix -> instance)
  }).toMap

  def size = routerKeys.size

  private lazy val routerKeys = guice.getAllBindings.keySet().asScala
    .filter(isRouter)

  private def isRouter(key: Key[_]): Boolean = {
    val name = key.getTypeLiteral.getType.getTypeName
    try {
      val c = environment.classLoader.loadClass(name)
      (c.isAssignableFrom(classOf[Router]) || classOf[Router].isAssignableFrom(c)) &&
        !name.startsWith("play.api") && !name.contains("ModularRouter")
    } catch {
      case e =>
        println(s"!! Failed to load class $name")
        false
    }
  }

}
