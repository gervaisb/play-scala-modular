package controllers

import example.Greeting
import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class MyModuleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def module() = Action {
    Ok(Greeting.from(getClass).asText())
  }
}
