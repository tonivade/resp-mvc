package com.github.tonivade.resp.mvc.repository;

import org.springframework.data.repository.CrudRepository;

/**
 * There's no reactive support for JPA
 */
public interface UserRepository extends CrudRepository<User, String> {
}
