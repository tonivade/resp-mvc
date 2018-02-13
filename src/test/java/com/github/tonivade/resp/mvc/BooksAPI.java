package com.github.tonivade.resp.mvc;

import static com.github.tonivade.zeromock.Extractors.asInteger;
import static com.github.tonivade.zeromock.Extractors.asString;
import static com.github.tonivade.zeromock.Extractors.body;
import static com.github.tonivade.zeromock.Extractors.pathParam;
import static com.github.tonivade.zeromock.Handlers.join;
import static com.github.tonivade.zeromock.Handlers.split;

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tonivade.zeromock.HttpRequest;

@Component
public class BooksAPI {
  @Autowired
  private BooksService service;

  public Supplier<Object> findAll() {
    return service::findAll;
  }

  public Function<HttpRequest, Object> update() {
    return join(getBookId(), getBookTitle()).andThen(split(service::update));
  }

  public Function<HttpRequest, Object> find() {
    return getBookId().andThen(service::find);
  }

  public Function<HttpRequest, Object> create() {
    return body().andThen(asString()).andThen(service::create);
  }

  public Function<HttpRequest, Object> delete() {
    return getBookId().andThen(service::delete);
  }

  private static Function<HttpRequest, Integer> getBookId() {
    return pathParam(1).andThen(asInteger());
  }

  private static Function<HttpRequest, String> getBookTitle() {
    return body().andThen(asString());
  }
}
