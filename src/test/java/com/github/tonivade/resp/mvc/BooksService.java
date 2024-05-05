/*
 * Copyright (c) 2018-2024, Antonio Gabriel Muñoz Conejo <me at tonivade dot es>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc;

import java.util.List;

public class BooksService {

  public List<Book> findAll() {
    return List.of(new Book(1, "title"));
  }

  public Book find(Integer id) {
    return new Book(id, "title");
  }

  public Book create(String title) {
    return new Book(1, title);
  }

  public Book update(Integer id, String title) {
    return new Book(id, title);
  }

  public void delete(Integer id) {
  }

  public static record Book(Integer id, String titgle) {

  }
}
