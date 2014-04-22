import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "ddsl-play2-consumer-example"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.typesafe.play" %% "play-java"  % "2.2.2",
      "com.kjetland" %% "ddsl-play2" % "1.3"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here
      resolvers ++= Seq(
        Resolver.file("Local ivy Repository", file(Path.userHome+"/.ivy2/local/"))(Resolver.ivyStylePatterns),
        "mbknor github Repository" at "https://raw.githubusercontent.com/mbknor/mbknor.github.com/master/m2repo/releases"
      )

    )

}
