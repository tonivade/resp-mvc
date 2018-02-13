/*
 * Copyright (c) 2018, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
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
  public BooksService booksModule() {
    return new BooksService();
  }
  
  @Bean
  public HttpService books(BooksAPI books) {
    return new HttpService("books")
      .when(post("/books"), created(books.create()))
      .when(get("/books"), ok(books.findAll()))
      .when(get("/books/:id"), ok(books.find()))
      .when(delete("/books/:id"), ok(books.delete()))
      .when(put("/books/:id"), ok(books.update()));
  }
}
