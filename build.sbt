import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

name := "lift basic heroku"

version := "0.0.3"

organization := "net.liftweb"

scalaVersion := "2.10.3"

resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
  "staging"       at "http://oss.sonatype.org/content/repositories/staging",
  "releases"      at "http://oss.sonatype.org/content/repositories/releases"
)

seq(webSettings :_*)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.6-M2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "net.liftmodules"   %% "lift-jquery-module_2.6" % "2.5",
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.14.v20131031"  % "container,test,compile",
    "javax.servlet"     % "javax.servlet-api"   % "3.1.0" % "container,test",
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        %% "specs2"             % "1.14"             % "test",
  )
}

