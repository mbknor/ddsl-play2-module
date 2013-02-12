import sbt._
import Keys._

object DdslPlay2ModuleBuild extends Build {

  val mbknorGithubRepoUrl = "http://mbknor.github.com/m2repo/releases/"
  val typesafeRepoUrl = "http://repo.typesafe.com/typesafe/releases/"

  lazy val DdslPlay2ModuleProject = Project(
    "ddsl-play2",
    new File("."),
    settings = BuildSettings.buildSettings ++ Seq(
      libraryDependencies := Dependencies.runtime,
      publishMavenStyle := true,
      publishTo := Some(Resolvers.mbknorRepository),
      scalacOptions ++= Seq("-Xlint","-deprecation", "-unchecked","-encoding", "utf8"),
      javacOptions ++= Seq("-encoding", "utf8", "-g"),
      resolvers ++= Seq(DefaultMavenRepository, Resolvers.mbknorGithubRepo, Resolvers.typesafe)
    )
  )


  object Resolvers {
    val mbknorRepository = Resolver.ssh("my local mbknor repo", "localhost", "~/projects/mbknor/mbknor.github.com/m2repo/releases/")(Resolver.mavenStylePatterns)
    val mbknorGithubRepo = "mbknor github Repository" at mbknorGithubRepoUrl
    val typesafe = "Typesafe Repository" at typesafeRepoUrl
  }

  object Dependencies {

    val runtime = Seq(
      "play"                    %% "play"              % "2.1.0" % "provided" notTransitive(),
      "ch.qos.logback"           % "logback-core"      % "1.0.7" % "provided",
      "ch.qos.logback"           % "logback-classic"   % "1.0.7" % "provided",
      "org.scala-lang"           % "scala-library"     % BuildSettings.buildScalaVersion,
      "com.kjetland"            %% "ddsl"              % "0.3.3",
      "org.apache.zookeeper"     % "zookeeper"         % "3.4.3" intransitive(), // Explicit include here to make it intransitive..
      "joda-time"                % "joda-time"         % "2.1",
      "org.joda"                 % "joda-convert"      % "1.2",
      "commons-codec"            % "commons-codec"     % "1.4",
      "org.scalatest"           %% "scalatest"         % "1.9.1"  % "test",
      "org.specs2"              %% "specs2"            % "1.12.3" % "test"
    )
  }


  object BuildSettings {

    val buildOrganization = "com.kjetland"
    val buildVersion      = "1.2"
    val buildScalaVersion = "2.10.0"
    val buildSbtVersion   = "0.12.1"

    val buildSettings = Defaults.defaultSettings ++ Seq (
      organization   := buildOrganization,
      version        := buildVersion,
      scalaVersion   := buildScalaVersion
    )

  }


}

