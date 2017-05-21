package com.github.tonivade.resp.mvc;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.github.tonivade.resp.mvc.mapping.UserAPI;
import com.github.tonivade.resp.mvc.repository.User;

@SpringBootApplication
public class RespMvcApplication {

  @Bean
  public RouterFunction<?> routerFunction(UserAPI user) {
    return route(GET("/user/{id}"),
        request -> ServerResponse.ok().body(user.get(request.pathVariable("id")), User.class));
  }

  public static void main(String[] args) {
    SpringApplication.run(RespMvcApplication.class, args);
  }
}
