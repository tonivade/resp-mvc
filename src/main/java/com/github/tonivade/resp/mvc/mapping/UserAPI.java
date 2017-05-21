package com.github.tonivade.resp.mvc.mapping;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.github.tonivade.resp.mvc.repository.User;
import com.github.tonivade.resp.mvc.repository.UserRepository;

import reactor.core.publisher.Mono;

@Component
public class UserAPI {

  @Autowired
  private UserRepository repository;

  @PostConstruct
  public void init() {
    repository.save(new User("agmunoz", "Antonio Muñoz"));
    repository.save(new User("evledesma", "Vanessa Ledesma"));
  }

  public Mono<User> get(@PathVariable("id") String id) {
    return repository.findById(id).map(Mono::just).orElseGet(Mono::empty);
  }

}
