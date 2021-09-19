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
import static com.github.tonivade.zeromock.api.Serializers.objectToJson;

import java.lang.reflect.Type;
import java.util.List;

import com.github.tonivade.purefun.Consumer1;
import com.github.tonivade.purefun.Function1;
import com.github.tonivade.purefun.Function2;
import com.github.tonivade.purefun.Producer;
import com.github.tonivade.purefun.type.Try;
import com.github.tonivade.purejson.TypeToken;
import com.github.tonivade.resp.mvc.BooksService.Book;
import com.github.tonivade.zeromock.api.Bytes;
import com.github.tonivade.zeromock.api.HttpRequest;
import com.github.tonivade.zeromock.api.RequestHandler;

public class BooksAPI {

  private final BooksService service;

  public BooksAPI(BooksService service) {
    this.service = service;
  }

  public RequestHandler findAll() {
    Producer<List<Book>> findAll = service::findAll;
    return okBooks(findAll.asFunction());
  }

  public RequestHandler update() {
    Function2<Integer, String, Book> update = service::update;
    Function1<HttpRequest, Book> compose = update.compose(getBookId(), getBookTitle());
    return okBook(compose);
  }

  public RequestHandler find() {
    return okBook(getBookId().andThen(service::find));
  }

  public RequestHandler create() {
    return createdBook(getBookTitle().andThen(service::create));
  }

  public RequestHandler delete() {
    Consumer1<Integer> delete = service::delete;
    return okBook(getBookId().andThen(delete.asFunction()));
  }

  private static Function1<HttpRequest, Integer> getBookId() {
    return pathParam(1).andThen(asInteger());
  }

  private static Function1<HttpRequest, String> getBookTitle() {
    return body().andThen(asString());
  }

  private static <T> RequestHandler okBook(Function1<HttpRequest, T> handler) {
    Function1<HttpRequest, Try<Bytes>> serialized = handler.andThen(objectToJson(Book.class));
    return ok(serialized.andThen(Try::get)).andThen(contentJson())::apply;
  }

  private static <T> RequestHandler okBooks(Function1<HttpRequest, T> handler) {
    Type listOfBooks = new TypeToken<List<Book>>() {}.getType();
    Function1<HttpRequest, Try<Bytes>> serialized = handler.andThen(objectToJson(listOfBooks));
    return ok(serialized.andThen(Try::get)).andThen(contentJson())::apply;
  }

  private static <T> RequestHandler createdBook(Function1<HttpRequest, T> handler) {
    Function1<HttpRequest, Try<Bytes>> serialized = handler.andThen(objectToJson(Book.class));
    return created(serialized.andThen(Try::get)).andThen(contentJson())::apply;
  }
}
