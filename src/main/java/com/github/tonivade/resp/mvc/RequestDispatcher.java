package com.github.tonivade.resp.mvc;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.tonivade.resp.command.Request;
import com.github.tonivade.resp.protocol.RedisToken;
import com.github.tonivade.zeromock.HttpHeaders;
import com.github.tonivade.zeromock.HttpMethod;
import com.github.tonivade.zeromock.HttpParams;
import com.github.tonivade.zeromock.HttpRequest;
import com.github.tonivade.zeromock.HttpResponse;
import com.github.tonivade.zeromock.HttpService;
import com.github.tonivade.zeromock.Path;

@Component
public class RequestDispatcher {
  private static final String ROOT = "/";

  public Map<String, HttpService> services;
  
  public RequestDispatcher(List<HttpService> services) {
    this.services = services.stream().collect(toMap(service -> ROOT + service.name(), identity()));
  }

  public RedisToken execute(Request request) {
    HttpRequest httpRequest = convertToHttpRequest(request);
    Optional<HttpResponse> httpResponse = execute(httpRequest);
    return httpResponse.map(res -> convertToHttpResponse(res)).orElse(RedisToken.error("not found"));
  }

  private HttpRequest convertToHttpRequest(Request request) {
    URI uri = URI.create(request.getParam(0).toString());
    return new HttpRequest(HttpMethod.valueOf(request.getCommand().toUpperCase()), 
                           new Path(uri.getPath()), 
                           null, 
                           HttpHeaders.empty(), 
                           new HttpParams(uri.getQuery()));
  }

  private RedisToken convertToHttpResponse(HttpResponse httpResponse) {
    return RedisToken.string(httpResponse.body().toString());
  }

  private Optional<HttpResponse> execute(HttpRequest request) {
    return findService(request).flatMap(service -> service.handle(request.dropOneLevel()));
  }
  
  private Optional<HttpService> findService(HttpRequest request) {
    return Optional.ofNullable(services.get(ROOT + request.path().getAt(0)));
  }
}
