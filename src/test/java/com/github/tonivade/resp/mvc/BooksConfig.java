/*
 * Copyright (c) 2018-2024, Antonio Gabriel Muñoz Conejo <me at tonivade dot es>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc;

import static com.github.tonivade.zeromock.api.Matchers.delete;
import static com.github.tonivade.zeromock.api.Matchers.get;
import static com.github.tonivade.zeromock.api.Matchers.post;
import static com.github.tonivade.zeromock.api.Matchers.put;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.tonivade.zeromock.api.HttpService;

@Configuration
public class BooksConfig {
  @Bean
  public BooksService booksService() {
    return new BooksService();
  }

  @Bean
  public BooksAPI booksAPI(BooksService service) {
    return new BooksAPI(service);
  }

  @Bean
  public HttpService books(BooksAPI books) {
    return new HttpService("books")
      .when(post("/books")).then(books.create())
      .when(get("/books")).then(books.findAll())
      .when(get("/books/:id")).then(books.find())
      .when(delete("/books/:id")).then(books.delete())
      .when(put("/books/:id")).then(books.update());
  }
}
