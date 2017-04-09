package com.github.tonivade.resp.mvc.command;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.annotation.ParamLength;
import com.github.tonivade.resp.command.ICommand;
import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.mvc.dispatcher.RespRequestDispatcher;
import com.github.tonivade.resp.protocol.RedisToken;

@Command("get")
@ParamLength(1)
public class GetCommand implements ICommand {
    @Autowired
    private RespRequestDispatcher dispatcher;

    @Override
    public RedisToken<?> execute(IRequest request) {
        return dispatcher.dispatch(request);
    }
}
