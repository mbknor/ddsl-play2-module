package controllers;

import com.kjetland.ddsl.utils.NetUtils;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
  public static Result index() {
  	Logger.info("Processing request");
    String port = Play.application().configuration().getString("http.port");
    if ( port == null) port = "9000";

    String ip = NetUtils.resolveLocalPublicIP();

    return ok("Answer from " + ip + ":" + port + " (ts: "+System.currentTimeMillis()+")");
  }
  
}