package com.github.tonivade.resp.mvc;

import static com.github.tonivade.zeromock.Handlers.created;
import static com.github.tonivade.zeromock.Handlers.ok;
import static com.github.tonivade.zeromock.Predicates.delete;
import static com.github.tonivade.zeromock.Predicates.get;
import static com.github.tonivade.zeromock.Predicates.post;
import static com.github.tonivade.zeromock.Predicates.put;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.tonivade.zeromock.HttpService;

@Configuration
public class BooksConfig {
  @Bean
  public BooksModule booksModule() {
    return new BooksModule();
  }
  
  @Bean
  public HttpService books(BooksModule module) {
    return new HttpService("books")
      .when(post("/books"), created(request -> module.createBook(request.body().toString())))
      .when(get("/books"), ok(request -> module.findAllBooks()))
      .when(get("/books/:id"), ok(request -> module.findBook(request.pathParamAsInteger(1))))
      .when(delete("/books/:id"), ok(request -> module.deleteBook(request.pathParamAsInteger(1))))
      .when(put("/books/:id"), ok(request -> module.updateBook(request.pathParamAsInteger(1), request.body().toString())));
  }
}
