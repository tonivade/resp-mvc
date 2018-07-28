/*
 * Copyright (c) 2018, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc;

import static com.github.tonivade.zeromock.api.Extractors.asInteger;
import static com.github.tonivade.zeromock.api.Extractors.asString;
import static com.github.tonivade.zeromock.api.Extractors.body;
import static com.github.tonivade.zeromock.api.Extractors.pathParam;
import static com.github.tonivade.zeromock.api.Handlers.created;
import static com.github.tonivade.zeromock.api.Handlers.ok;
import static com.github.tonivade.zeromock.api.Headers.contentJson;
import static com.github.tonivade.zeromock.api.Serializers.json;

import java.util.List;

import com.github.tonivade.purefun.Consumer1;
import com.github.tonivade.purefun.Function1;
import com.github.tonivade.purefun.Function2;
import com.github.tonivade.purefun.Producer;
import com.github.tonivade.resp.mvc.BooksService.Book;
import com.github.tonivade.zeromock.api.HttpRequest;
import com.github.tonivade.zeromock.api.RequestHandler;

public class BooksAPI {

  private final BooksService service;

  public BooksAPI(BooksService service) {
    this.service = service;
  }

  public RequestHandler findAll() {
    Producer<List<Book>> findAll = service::findAll;
    return okJson(findAll.asFunction());
  }

  public RequestHandler update() {
    Function2<Integer, String, Book> update = service::update;
    return okJson(update.compose(getBookId(), getBookTitle()));
  }

  public RequestHandler find() {
    return okJson(getBookId().andThen(service::find));
  }

  public RequestHandler create() {
    return createdJson(getBookTitle().andThen(service::create));
  }

  public RequestHandler delete() {
    Consumer1<Integer> delete = service::delete;
    return okJson(getBookId().andThen(delete.asFunction()));
  }

  private static Function1<HttpRequest, Integer> getBookId() {
    return pathParam(1).andThen(asInteger());
  }

  private static Function1<HttpRequest, String> getBookTitle() {
    return body().andThen(asString());
  }

  private static <T> RequestHandler okJson(Function1<HttpRequest, T> handler) {
    return ok(handler.andThen(json())).andThen(contentJson())::apply;
  }

  private static <T> RequestHandler createdJson(Function1<HttpRequest, T> handler) {
    return created(handler.andThen(json())).andThen(contentJson())::apply;
  }
}
