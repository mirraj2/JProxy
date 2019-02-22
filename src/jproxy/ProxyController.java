package jproxy;

import static com.google.common.base.Preconditions.checkState;
import static ox.util.Utils.isNullOrEmpty;

import java.io.File;

import bowser.Controller;
import bowser.Handler;
import bowser.Request;
import ox.Config;
import ox.IO;
import ox.Json;
import ox.Log;

public class ProxyController extends Controller {

  private static final Config config = Config.load("jproxy");

  private final String token = config.get("token");

  @Override
  public void init() {
    route("POST", "/get").to(get);
    route("GET", "/file").to(getFile);
    route("GET", "/ls").to(listFiles);
  }

  private final Handler getFile = (request, response) -> {
    authenticate(request);

    File file = new File(request.param("path"));
    checkState(file.exists());

    IO.from(file).to(response.getOutputStream());
  };

  public final Handler listFiles = (request, response) -> {
    authenticate(request);

    File file = new File(request.param("path"));
    checkState(file.exists());
    checkState(file.isDirectory());

    Json ret = Json.array();
    for (File child : file.listFiles()) {
      ret.add(child.getAbsolutePath());
    }
    response.write(ret);
  };

  private void authenticate(Request request) {
    String token = request.param("token");
    if (isNullOrEmpty(this.token)) {
      throw new RuntimeException("Please set a token.");
    }

    checkState(token.equals(this.token), "Bad token.");
  }

  private final Handler get = (request, response) -> {
    String url = request.getJson().get("url");

    Log.info(url);
    IO.fromURL(url).to(response.getOutputStream());
  };

}
