package jproxy;

import java.util.Map;

import ox.IO;
import ox.Log;

public class ProxyLambdaFunction {

  public String get(Map<String, String> input) {
    Log.debug(input);
    // Map<String, String> query = (Map<String, String>) e.get("queryStringParameters");
    String url = input.get("url");
    return IO.fromURL(url).toString();
  }
  
}
