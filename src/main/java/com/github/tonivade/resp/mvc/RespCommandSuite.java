package com.github.tonivade.resp.mvc;

import org.springframework.http.HttpMethod;

import com.github.tonivade.resp.command.CommandSuite;
import com.github.tonivade.resp.command.CommandWrapperFactory;
import com.github.tonivade.resp.command.ICommand;
import com.github.tonivade.resp.mvc.dispatcher.RespRequestDispatcher;

public class RespCommandSuite extends CommandSuite {

  public RespCommandSuite(CommandWrapperFactory factory, RespRequestDispatcher dispatcher) {
    super(factory);
    addCommand(HttpMethod.GET, dispatcher::dispatch);
    addCommand(HttpMethod.PUT, dispatcher::dispatch);
    addCommand(HttpMethod.POST, dispatcher::dispatch);
    addCommand(HttpMethod.DELETE, dispatcher::dispatch);
    addCommand(HttpMethod.PATCH, dispatcher::dispatch);
    addCommand(HttpMethod.HEAD, dispatcher::dispatch);
    addCommand(HttpMethod.OPTIONS, dispatcher::dispatch);
    addCommand(HttpMethod.TRACE, dispatcher::dispatch);
  }

  private void addCommand(HttpMethod method, ICommand command) {
    addCommand(method.name(), command);
  }
}
