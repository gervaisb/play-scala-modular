package controllers

import example.Greeting
import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def greet() = Action {
    Ok(Greeting.from(getClass).asText())
  }

}
