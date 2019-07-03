package example

case class GreetingTo(private val name: String) {

  def asText() = s"Hello $name (from domain)"
}
