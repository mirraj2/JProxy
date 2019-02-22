package jproxy;

import java.io.File;

import bowser.WebServer;
import ox.Config;
import ox.Log;
import ox.OS;

public class ProxyServer {

  private static final Config config = Config.load("jproxy");

  public void run() {
    boolean devMode = true;

    int port = config.getInt("port", devMode ? 8080 : 443);

    WebServer server = new WebServer("JProxy", port, devMode)
        .controller(new ProxyController());

    server.start();

    Log.info("Server started on port " + port);
  }

  public static void main(String[] args) {
    Log.logToFolder(new File(OS.getAppFolder("jproxy"), "log"));

    new ProxyServer().run();
  }

}
