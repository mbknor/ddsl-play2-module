package controllers;

import com.kjetland.ddsl.play2.DDSL;
import com.kjetland.ddsl.utils.NetUtils;
import play.*;

import play.libs.F;
import play.libs.WS;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    protected static String getThisServerInfo() {
        String port = Play.application().configuration().getString("http.port");
        if ( port == null) port = "9000";

        String ip = NetUtils.resolveLocalPublicIP();

        return ip+":"+port;
    }

  public static Result index() {
      final String apiUrl = DDSL.getBestUrl("Play2ExampleServer", "1.0", "http");
      Logger.info("Using apiUrl: " + apiUrl);
      F.Promise<WS.Response> responsePromise = WS.url(apiUrl).get();

      return async(responsePromise.map(new F.Function<WS.Response, Result>() {
          @Override
          public Result apply(WS.Response wsResponse) throws Throwable {

              if (wsResponse.getStatus() != 200) {
                  return ok("This server " + getThisServerInfo() + " got error("+wsResponse.getStatus()+") from external apiServer at: " + apiUrl);
              } else {
                  String result = wsResponse.getBody();
                  return ok("(ts:"+System.currentTimeMillis()+") This server " + getThisServerInfo() + " got response from apiServer at " + apiUrl + ": " + result);
              }
          }
      }));
  }
  
}