import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "ddsl-play2-producer-example"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "com.kjetland" %% "ddsl-play2" % "1.0"
    )


    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
		resolvers ++= Seq(
          Resolver.file("Local ivy Repository", file(Path.userHome+"/.ivy2/local/"))(Resolver.ivyStylePatterns),
          "mbknor github Repository" at "http://mbknor.github.com/m2repo/releases/"
        )
    )

}
