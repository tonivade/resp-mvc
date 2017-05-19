package com.github.tonivade.resp.mvc.mapping;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.tonivade.resp.mvc.repository.User;
import com.github.tonivade.resp.mvc.repository.UserRepository;

@RestController
public class UserAPI {

  @Autowired
  private UserRepository repository;

  @PostConstruct
  public void init() {
    repository.save(new User("agmunoz", "Antonio Muñoz"));
    repository.save(new User("evledesma", "Vanessa Ledesma"));
  }

  @RequestMapping(method = RequestMethod.GET, path = "/user/{id}", produces = "application/resp")
  public User get(@PathVariable("id") String id) {
    return repository.findById(id).orElse(null);
  }

}
