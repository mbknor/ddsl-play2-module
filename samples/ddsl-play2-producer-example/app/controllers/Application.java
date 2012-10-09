package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
  public static Result index() {
      String port = Play.application().configuration().getString("http.port");
      if ( port == null) port = "9000";

    return ok("port: " + port);
  }
  
}