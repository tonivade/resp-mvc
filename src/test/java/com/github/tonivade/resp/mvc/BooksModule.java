package com.github.tonivade.resp.mvc;

import static java.util.Arrays.asList;

import java.util.List;

public class BooksModule {

  public List<Book> findAll() {
    return asList(new Book(1, "title"));
  }

  public Book find(Integer id) {
    return new Book(id, "title");
  }

  public Book create(String title) {
    return new Book(1, title);
  }

  public Object update(Integer id, String title) {
    return new Book(id, title);
  }

  public Void delete(Integer id) {
    return null;
  }
  
  public static class Book {
    private final Integer id;
    private final String title;

    public Book(Integer id, String title) {
      this.id = id;
      this.title = title;
    }
    
    @Override
    public String toString() {
      return "Book(id:" + id + ",title:" + title + ")";
    }
  }
}
