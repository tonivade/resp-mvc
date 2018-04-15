/*
 * Copyright (c) 2018, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc;

import static com.github.tonivade.zeromock.core.Extractors.asInteger;
import static com.github.tonivade.zeromock.core.Extractors.asString;
import static com.github.tonivade.zeromock.core.Extractors.body;
import static com.github.tonivade.zeromock.core.Extractors.pathParam;
import static com.github.tonivade.zeromock.core.Handler1.adapt;
import static com.github.tonivade.zeromock.core.Handler2.adapt;
import static com.github.tonivade.zeromock.core.Handlers.created;
import static com.github.tonivade.zeromock.core.Handlers.ok;
import static com.github.tonivade.zeromock.core.Headers.contentJson;
import static com.github.tonivade.zeromock.core.Serializers.json;

import com.github.tonivade.zeromock.core.Handler1;
import com.github.tonivade.zeromock.core.HttpRequest;
import com.github.tonivade.zeromock.core.RequestHandler;

public class BooksAPI {
  
  private final BooksService service;

  public BooksAPI(BooksService service) {
    this.service = service;
  }

  public RequestHandler findAll() {
    return okJson(adapt(service::findAll));
  }

  public RequestHandler update() {
    return okJson(adapt(service::update).compose(getBookId(), getBookTitle()));
  }

  public RequestHandler find() {
    return okJson(getBookId().andThen(service::find));
  }

  public RequestHandler create() {
    return createdJson(getBookTitle().andThen(service::create));
  }

  public RequestHandler delete() {
    return okJson(getBookId().andThen(adapt(service::delete)));
  }

  private static Handler1<HttpRequest, Integer> getBookId() {
    return pathParam(1).andThen(asInteger());
  }

  private static Handler1<HttpRequest, String> getBookTitle() {
    return body().andThen(asString());
  }
  
  private static <T> RequestHandler okJson(Handler1<HttpRequest, T> handler) {
    return ok(handler.andThen(json())).andThen(contentJson())::handle;
  }
  
  private static <T> RequestHandler createdJson(Handler1<HttpRequest, T> handler) {
    return created(handler.andThen(json())).andThen(contentJson())::handle;
  }
}
