package code.snippet

import java.util.Date

import net.liftweb.util.Helpers._

class HelloWorld {
  lazy val date = new Date()

  // replace the contents of the element with id "time" with the date
  def howdy = "#time *" #> date.toString

}