package com.github.tonivade.resp.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.tonivade.resp.RedisServer;
import com.github.tonivade.resp.command.CommandSuite;
import com.github.tonivade.resp.command.CommandWrapperFactory;
import com.github.tonivade.resp.mvc.command.GetCommand;

@SpringBootApplication
public class RespMvcApplication {

    @Value("${demo.host}")
    private String host;
    @Value("${demo.port}")
    private int port;

    @Bean(initMethod = "start")
    public RedisServer server(CommandSuite commands) {
        return new RedisServer(host, port, commands);
    }

    @Bean
    public CommandSuite commandSuite(CommandWrapperFactory factory) {
        return new CommandSuite(factory) {
            {
                addCommand(GetCommand.class);
            }
        };
    }

    @Bean
    public CommandWrapperFactory commandWrapperFactory(AutowireCapableBeanFactory factory) {
        return new SpringCommandWrapperFactory(factory);
    }

    @Bean
    public RespMessageConverter respMessageConverter() {
      return new RespMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(RespMvcApplication.class, args);
    }
}
