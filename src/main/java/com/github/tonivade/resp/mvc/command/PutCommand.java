package com.github.tonivade.resp.mvc.command;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.annotation.ParamLength;
import com.github.tonivade.resp.command.ICommand;
import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.command.IResponse;
import com.github.tonivade.resp.mvc.dispatcher.RespRequestDispatcher;

@Command("put")
@ParamLength(1)
public class PutCommand implements ICommand {
    @Autowired
    private RespRequestDispatcher dispatcher;

    @Override
    public void execute(IRequest request, IResponse response) {
        dispatcher.dispatch(request, response);
    }
}
