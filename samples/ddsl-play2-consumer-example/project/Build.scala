import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "ddsl-play2-consumer-example"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "play"         %% "play-java"  % "2.1.0",
      "com.kjetland" %% "ddsl-play2" % "1.2"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here
      resolvers ++= Seq(
        Resolver.file("Local ivy Repository", file(Path.userHome+"/.ivy2/local/"))(Resolver.ivyStylePatterns),
        "mbknor github Repository" at "http://mbknor.github.com/m2repo/releases/"
      )

    )

}
