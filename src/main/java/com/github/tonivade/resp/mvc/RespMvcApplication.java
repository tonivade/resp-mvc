package com.github.tonivade.resp.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.tonivade.resp.RespServer;
import com.github.tonivade.resp.command.CommandSuite;
import com.github.tonivade.resp.command.CommandWrapperFactory;
import com.github.tonivade.resp.mvc.dispatcher.RespRequestDispatcher;

@SpringBootApplication
public class RespMvcApplication {

  @Value("${demo.host}")
  private String host;
  @Value("${demo.port}")
  private int port;

  @Bean(initMethod = "start", destroyMethod = "stop")
  public RespServer server(CommandSuite commands) {
    return new RespServer(host, port, commands);
  }

  @Bean
  public CommandSuite commandSuite(CommandWrapperFactory factory, RespRequestDispatcher dispatcher) {
    return new RespCommandSuite(factory, dispatcher);
  }

  @Bean
  public CommandWrapperFactory commandWrapperFactory(AutowireCapableBeanFactory factory) {
    return new SpringCommandWrapperFactory(factory);
  }

  public static void main(String[] args) {
    SpringApplication.run(RespMvcApplication.class, args);
  }
}
