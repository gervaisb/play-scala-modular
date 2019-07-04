package module

import controllers.MyModuleController
import example.Greeting
import javax.inject.Inject
import play.api.http.HttpConfiguration
import play.api.{Configuration, Environment}
import play.api.inject._
import play.api.mvc.{Action, Handler, RequestHeader, Results}
import play.api.routing.Router.Routes
import play.api.routing.sird.GET
import play.api.routing.{Router, SimpleRouter}

class MyRouter @Inject()(ctrl: MyModuleController) extends SimpleRouter {

  import play.api.routing.sird._

  def routes: Routes = {
    case GET(p"/greet") => ctrl.module()
    case req => Action {
      Results.NotFound(s"No route found for '${req}' in " + getClass)
    }
  }

}

class MyModule extends Module {
  def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = Seq(
    bind[MyRouter].toSelf.eagerly()
  )
}
