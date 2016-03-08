package bootstrap.liftweb


import net.liftweb.common.Loggable
import net.liftweb.util.Props
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext


object JettyLancher {

  def main(args: Array[String]) {
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080

    val server = new Server(port)
    val context = new WebAppContext()
    context.setContextPath("/")
    context.setResourceBase("src/main/webapp")

    //    context.setEventListeners(Array(new ScalatraListener))

    server.setHandler(context)

    server.start
    server.join
  }
}