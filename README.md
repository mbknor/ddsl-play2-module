DDSL module for PlayFramework 2.0
=============

What is DDSL?
-------------

When using [DDSL](https://github.com/mbknor/ddsl) (Dynamic Distributed Service Locator) to plumb all your applications/services together,
you get your own "dynamic cloud" where you can just launch more instances of your servers to increase the
overall performance of your own "cloud". Please see [DDSL](https://github.com/mbknor/ddsl) for more info.

DDSL can be used in all java/scala based applications, but is a perfect match for your Play Framework application.

This is the Play 2.0 module, but you can also find a Play 1.2.x module [here](https://github.com/mbknor/ddsl-playframework-module).

How to use it in your Play 2 app?
==============

To enable the DDSL module in your Play 2.0 application, you have to add it as a dependency. Since DDSL is hosted on my custom repository, you also have to add the url to this repository. Your **project/Build.scala**-file should look something like this:

	import sbt._
	import Keys._
	import PlayProject._

	object ApplicationBuild extends Build {

	    val appName         = "TheNameOfYourOwnApp"
	    val appVersion      = "1.0-SNAPSHOT"

	    val appDependencies = Seq(
	      // ****** This the ddsl-play2-module dependency
	      "com.kjetland" %% "ddsl-play2" % "1.0"
	    )


	    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
	      // ****** Adding the custom repository url
			resolvers ++= Seq(
	          "mbknor github Repository" at "http://mbknor.github.com/m2repo/releases/"
	        )
	    )

	}

Broadcasting
-----------

If you want your application to automatically be broadcasted to DDSL (using serviceUp()) as long as it is running, you have to add the following to your **conf/application.conf**-file:

    # ddsl.zkhostslist should list all zookeeper nodes in you zookeeper network (comma seperated)
    ddsl.zkhostslist = localhost:2181

	# If you want to broadcast to DDSL that your app is online when it starts, you have to set ddsl.broadcastservice=true
	ddsl.broadcastservice=true

    # Specifies the environment used both when registering your app and when querying for locations
	ddsl.environment=test

	# If ddsl.broadcastservice=true is on, then you have to specify serviceId:
	# Identifies your application in the DDSL network.	
	ddsl.serviceid.type=http
	ddsl.serviceid.name=Play2ExampleServer
	ddsl.serviceid.version=1.0

When your application is automatically broadcasted as online, its url is generated like this:

	http://<the-ip-of-your-server>:<the-port-play-is-listening-on>/

Example

	http://10.0.0.1:9000/

If you are using a different port than 9000, you have to use the -Dhttp.port property. DDSL will not be able to detect your port if you (in dev mode) are starting play on a custom port like this: run 9010 


Querying
------------

If you want to be able to query DDSL for the serviceLocations/URLs of other external services/applications, you have to add this to your **conf/application.conf**-file:

	# ddsl.zkhostslist should list all zookeeper nodes in you zookeeper network (comma seperated)
    ddsl.zkhostslist = localhost:2181

    # Specifies the environment used both when registering your app and when querying for locations
	ddsl.environment=test

    # ClientID is used to identify the application that is querying DDSL for serviceLocations
	ddsl.clientid.name = play-client
	ddsl.clientid.version = 1.0

    # Number of milliseconds the DDSL client will cache the result before resolving it again
    # ddsl.client_cache_ttl_mills = 1000

Then you can use this code to query DDSL for a serviceLocation:

	final String apiUrl = DDSL.getBestUrl("Play2ExampleServer", "1.0", "http");

If you want to be able to access the other [DDSL-client](http://mbknor.github.com/ddsl-scaladoc/#com.kjetland.ddsl.DdslClient)-operations, you can get a reference to the client like this:

	DDSL.getClient();



Broadcasting and querying
-------------

You can of course add everything if you want to both broadcast your own service and query for other services.

Seeing what is online?
==============

To see what services are online, your can have a look at this:

* [DDSL-Status](https://github.com/mbknor/ddsl-status), a simple Play 1.2.x-app that is a webapp that shows all online services.
* [ddsl-cmdline-tool](https://github.com/mbknor/ddsl/tree/master/ddsl-cmdline-tool) - Simple command-line tool to query and excute all DDSL-operations.
* Create your own app that uses [DdslClient.getAllAvailableServices()](http://mbknor.github.com/ddsl-scaladoc/#com.kjetland.ddsl.DdslClient)

What about the Web frontend - loadbalancer ?
============

Please see this [post](https://plus.google.com/116342781887978516536/posts/NhUTkq9pFNo) about how to automatically configure nginx, squid, apache or any other reverse proxy/loadbalancer with online services in DDSL.

Samples
==============

You have to download [ZooKeeper](https://hadoop.apache.org/zookeeper/) and start it (with all defaults) before you can run the samples.

In the [samples](https://github.com/mbknor/ddsl-play2-module/tree/master/samples)-folder you can find two sample applications. Both applications broadcasts itself to DDSL, but [ddsl-play2-consumer-example](https://github.com/mbknor/ddsl-play2-module/tree/master/samples/ddsl-play2-consumer-example) finds a running [ddsl-play2-producer-example](https://github.com/mbknor/ddsl-play2-module/tree/master/samples/ddsl-play2-producer-example)-server and uses its "API" via lib.WS.

If you want to to start both the producer and the consumer on the same machine, you have to remember to start them using different ports.

Due to a limitation in Play 2.0, you cannot start them using "run 9010", since DDSL will not be able to detect that custom port. Instead you have to start them using -Dhttp.port=9010. Play 2.0 does not accept that parameter when running in dev mode, so you have to stage the app, then use **target/start -Dhttp.port=9010** like this:

Stage it:

	play clean stage

Run it on custom port

    target/start -Dhttp.port=9010

Good luck :)



