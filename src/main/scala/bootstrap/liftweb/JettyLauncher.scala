package bootstrap.liftweb


import net.liftweb.common.Loggable
import net.liftweb.util.Props
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext

import scala.util.Properties


object JettyLancher extends App with Loggable{

  def startLift(): Unit = {
    logger.info("starting Lift server")

    val port = System.getProperty(
      "jetty.port", Properties.envOrElse("PORT", "5432")).toInt

    logger.info(s"port number is $port")

    val server = new Server(port)
    val context = new WebAppContext("target/webapp", Props.get("jetty.contextPath").openOr("/"))
    server.setHandler(context)
    server.start()
    logger.info(s"Lift server started on port $port")
    server.join()
  }

  startLift()
}