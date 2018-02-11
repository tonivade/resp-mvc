package com.github.tonivade.resp.mvc;

import static com.github.tonivade.resp.protocol.RedisToken.error;
import static com.github.tonivade.resp.protocol.RedisToken.nullString;
import static com.github.tonivade.resp.protocol.RedisToken.string;

import java.net.URI;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.tonivade.resp.command.Request;
import com.github.tonivade.resp.protocol.RedisToken;
import com.github.tonivade.resp.protocol.SafeString;
import com.github.tonivade.zeromock.HttpHeaders;
import com.github.tonivade.zeromock.HttpMethod;
import com.github.tonivade.zeromock.HttpParams;
import com.github.tonivade.zeromock.HttpRequest;
import com.github.tonivade.zeromock.HttpResponse;
import com.github.tonivade.zeromock.HttpService;
import com.github.tonivade.zeromock.Path;

@Component
public class RequestDispatcher {
  public final HttpService root;
  
  public RequestDispatcher(HttpService root) {
    this.root = root;
  }

  public RedisToken execute(Request request) {
    HttpRequest httpRequest = convertToHttpRequest(request);
    Optional<HttpResponse> httpResponse = execute(httpRequest);
    return httpResponse.map(this::convertToHttpResponse).orElse(error("not found"));
  }

  private HttpRequest convertToHttpRequest(Request request) {
    URI uri = URI.create(request.getParam(0).toString());
    return new HttpRequest(HttpMethod.valueOf(request.getCommand().toUpperCase()), 
                           new Path(uri.getPath()), 
                           body(request), 
                           HttpHeaders.empty(), 
                           new HttpParams(uri.getQuery()));
  }

  private SafeString body(Request request) {
    return request.getOptionalParam(1).orElse(null);
  }

  private RedisToken convertToHttpResponse(HttpResponse httpResponse) {
    return httpResponse.body() != null ? string(httpResponse.body().toString()) : nullString();
  }

  private Optional<HttpResponse> execute(HttpRequest request) {
    return root.handle(request);
  }
}
