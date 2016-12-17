package com.github.tonivade.resp.mvc.command;

import static com.github.tonivade.resp.protocol.SafeString.safeString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.github.tonivade.resp.annotation.Command;
import com.github.tonivade.resp.annotation.ParamLength;
import com.github.tonivade.resp.command.ICommand;
import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.command.IResponse;

@Command("get")
@ParamLength(1)
public class GetCommand implements ICommand {
    @Autowired
    private HandlerMapping mappings;
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Override
    public void execute(IRequest request, IResponse response) {
        String query = request.getParam(0).toString();
        try {
            HttpServletRequest httpRequest = new HttpServletRequestImpl(query);
            HttpServletResponse httpResponse = new HttpServletResponseImpl();
            HandlerExecutionChain handlerExecutionChain = mappings.getHandler(httpRequest);
            if (handlerExecutionChain != null) {
                handlerAdapter.handle(httpRequest, httpResponse, handlerExecutionChain.getHandler());
                response.addBulkStr(safeString(httpResponse.toString()));
            } else {
                response.addError("mapping not found: " + query);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.addError(e.getMessage());
        }
    }
}
