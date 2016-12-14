package com.github.tonivade.resp.mvc.command;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.command.ICommand;
import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.command.IResponse;

@Command("get")
public class GetCommand implements ICommand {
    @Override
    public void execute(IRequest request, IResponse response) {
        response.addSimpleStr("OK");
    }
}
