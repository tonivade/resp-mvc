/*
 * Copyright (c) 2018-2023, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc.command;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.command.Request;
import com.github.tonivade.resp.command.RespCommand;
import com.github.tonivade.resp.mvc.RequestDispatcher;
import com.github.tonivade.resp.protocol.RedisToken;

@Command("patch")
public class PatchCommand implements RespCommand {
  @Autowired
  private RequestDispatcher dispatcher;

  @Override
  public RedisToken execute(Request request) {
    return dispatcher.execute(request);
  }
}
