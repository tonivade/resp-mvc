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
      .when(post("/books"), created(module::createBook))
      .when(get("/books"), ok(module::findAllBooks))
      .when(get("/books/:id"), ok(module::findBook))
      .when(delete("/books/:id"), ok(module::deleteBook))
      .when(put("/books/:id"), ok(module::updateBook));
  }
}
