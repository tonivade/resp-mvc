/*
 * Copyright (c) 2018, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc;

import static com.github.tonivade.resp.protocol.RedisToken.error;
import static com.github.tonivade.resp.protocol.RedisToken.nullString;
import static com.github.tonivade.resp.protocol.RedisToken.string;
import static com.github.tonivade.zeromock.api.Bytes.empty;
import static java.util.Objects.requireNonNull;

import java.net.URI;

import org.springframework.stereotype.Component;

import com.github.tonivade.resp.command.Request;
import com.github.tonivade.resp.protocol.RedisToken;
import com.github.tonivade.resp.protocol.SafeString;
import com.github.tonivade.zeromock.api.Bytes;
import com.github.tonivade.zeromock.api.HttpHeaders;
import com.github.tonivade.zeromock.api.HttpMethod;
import com.github.tonivade.zeromock.api.HttpParams;
import com.github.tonivade.zeromock.api.HttpPath;
import com.github.tonivade.zeromock.api.HttpRequest;
import com.github.tonivade.zeromock.api.HttpResponse;
import com.github.tonivade.zeromock.api.HttpService;
import com.github.tonivade.zeromock.core.Option;

@Component
public class RequestDispatcher {

  public final HttpService root;
  
  public RequestDispatcher(HttpService root) {
    this.root = requireNonNull(root);
  }

  public RedisToken execute(Request request) {
    HttpRequest httpRequest = convertToHttpRequest(request);
    Option<HttpResponse> httpResponse = execute(httpRequest);
    return httpResponse.map(this::convertToHttpResponse).orElse(error("not found"));
  }

  private HttpRequest convertToHttpRequest(Request request) {
    URI uri = URI.create(request.getParam(0).toString());
    return new HttpRequest(HttpMethod.valueOf(request.getCommand().toUpperCase()), 
                           HttpPath.from(uri.getPath()), 
                           body(request), 
                           HttpHeaders.empty(), 
                           new HttpParams(uri.getQuery()));
  }

  private Bytes body(Request request) {
    return request.getOptionalParam(1)
        .map(SafeString::getBytes)
        .map(Bytes::fromArray)
        .orElse(empty());
  }

  private RedisToken convertToHttpResponse(HttpResponse httpResponse) {
    return !httpResponse.body().isEmpty() ? 
        string(new SafeString(httpResponse.body().getBuffer())) : nullString();
  }

  private Option<HttpResponse> execute(HttpRequest request) {
    return root.execute(request);
  }
}
