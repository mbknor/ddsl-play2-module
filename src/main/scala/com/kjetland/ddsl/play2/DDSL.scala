package com.kjetland.ddsl.play2


import com.kjetland.ddsl.DdslClient
import com.kjetland.ddsl.model.{ServiceRequest, ServiceId, ClientId}
import play.api.Play

object DDSL extends PropsUtils{

  def getDdslPluginInstance() : DdslPlugin = Play.current.plugin(classOf[DdslPlugin]).get

  def getClient():DdslClient = getDdslPluginInstance().client

  def getBestUrl( serviceName:String, serviceVersion:String, serviceType:String ):String = {
    //load config
    //ddsl.clientid.name=PlayExampleClient
    //ddsl.clientid.version=1.0
    val ddslPlugin = getDdslPluginInstance()
    val clientId = ClientId( ddslPlugin.ddslEnvironment,
      ddslPlugin.getProp("ddsl.clientid.name", "play-client", false),
      ddslPlugin.getProp("ddsl.clientid.version", "1.0", false),
      null)

    val serviceId = ServiceId(ddslPlugin.ddslEnvironment, serviceType, serviceName, serviceVersion)
    val sr = ServiceRequest( serviceId, clientId)
    return getClient().getBestServiceLocation(sr).url
  }

}
