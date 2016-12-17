package com.github.tonivade.resp.mvc.repository;

import static java.util.Objects.requireNonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class User
{
  @Column
  @Id
  private String id;
  @Column
  private String name;

  public User() { }

  public User(String id, String name)
  {
    setId(id);
    setName(name);
  }

  public String getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  protected void setId(String id)
  {
    this.id = requireNonNull(id);
  }

  protected void setName(String name)
  {
    this.name = requireNonNull(name);
  }
}
