package example

object Greeting {
  def apply(): Greeting = new Greeting()

  def from(location: String): Greeting = new Greeting(Some(location))

  def from(location: Class[_]): Greeting = from(location.getName)
}

case class Greeting(private val location: Option[String] = None) {
  def asText() = location match {
    case Some(place) => s"Greeting from $place"
    case None        => s"Greeting"
  }
}
