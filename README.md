DDSL module for PlayFramework 2.0
=============

What is DDSL?
-------------

When using [DDSL](https://github.com/mbknor/ddsl) (Dynamic Distributed Service Locator) to plumb all your applications/services together,
you get your own "dynamic cloud" where you can just launch more instances of your servers to increase the
overall performance of your own "cloud". Please see [DDSL](https://github.com/mbknor/ddsl) for more info.

DDSL Can be used in all java/scala based applications but is a perfect match for your Play Framework application.

This is the Play 2.0 module, but you can also find a Play 1.2.x module [here](https://github.com/mbknor/ddsl-playframework-module).

How to use it in your Play 2 app?
==============

To enable the DDSL module in your Play 2.0 application, you have to add it as a dependency. Since DDSL is hosted on my custom repository, you also have to add the url to this repository. Your **projects/Build.scala**-file should look something like this:

	import sbt._
	import Keys._
	import PlayProject._

	object ApplicationBuild extends Build {

	    val appName         = "TheNameOfYourOwnApp"
	    val appVersion      = "1.0-SNAPSHOT"

	    val appDependencies = Seq(
	      // Add your project dependencies here,
	      "com.kjetland" %% "ddsl-play2" % "1.0"
	    )


	    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
	      // Add your own project settings here      
			resolvers ++= Seq(
	          "mbknor github Repository" at "http://mbknor.github.com/m2repo/releases/"
	        )
	    )

	}

If you want your application to automatically be broadcasted to DDSL (using serviceUp()) as long as it is running, you have to add the following to your **conf/application.conf**-file:

	# If you want to broadcast to DDSL that your app is online when it starts, you have to set ddsl.broadcastservice=true
	ddsl.broadcastservice=true

    # Specifies the environment used both when registering your app and when querying for locations
	ddsl.environment=test

	# If ddsl.broadcastservice=true is on, then you have to specify serviceId:
	# Identifies your application in the DDSL network.	
	ddsl.serviceid.type=http
	ddsl.serviceid.name=Play2ExampleServer
	ddsl.serviceid.version=1.0


If you want to be able to query DDSL for the serviceLocations/URLs of other external services/applications, you have to add this to your **conf/application.conf**-file:

    # Specifies the environment used both when registering your app and when querying for locations
	ddsl.environment=test

    # ClientID is used to identify the application that is querying DDSL for serviceLocations
	ddsl.clientid.name = play-client
	ddsl.clientid.version = 1.0

    # Number of milliseconds the DDSL client will cache the result before resolving it again
    # ddsl.client_cache_ttl_mills = 1000

You can of course add everything if you want to both broadcast your own service and query for other services.

When your application is automatically broadcasted as online, its url is generated like this:

	http://<the-ip-of-your-server>:<the-port-play-is-listening-on>/

Example

	http://10.0.0.1:9000/

If you are using a different port than 9000, you have to use the -Dhttp.port property. DDSL will not be able to detect your port if you (in dev mode) are starting play on a custom port like this: run 9010 

Samples
==============

In the [samples](https://github.com/mbknor/ddsl-play2-module/tree/master/samples)-folder you can find two sample applications. Both applications broadcasts itself to DDSL, but [ddsl-play2-consumer-example](https://github.com/mbknor/ddsl-play2-module/tree/master/samples/ddsl-play2-consumer-example) finds a running [ddsl-play2-producer-example](https://github.com/mbknor/ddsl-play2-module/tree/master/samples/ddsl-play2-producer-example)-server and uses its "API" with WS.






