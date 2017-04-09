package com.github.tonivade.resp.mvc.dispatcher;

import static com.github.tonivade.resp.protocol.RedisToken.error;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.github.tonivade.resp.command.IRequest;
import com.github.tonivade.resp.mvc.RespMvcReturnValueHandler;
import com.github.tonivade.resp.protocol.RedisToken;
import com.github.tonivade.resp.protocol.RespSerializer;

@Controller
public class RespRequestDispatcher {
    @Autowired
    private HandlerMapping mappings;
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    private final RespSerializer serializer = new RespSerializer();

    @PostConstruct
    public void init() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
        handlers.add(new RespMvcReturnValueHandler());
        handlers.addAll(handlerAdapter.getReturnValueHandlers());
        handlerAdapter.setReturnValueHandlers(handlers);
    }

    public RedisToken<?> dispatch(IRequest request) {
        try {
            String command = request.getCommand();
            String query = request.getParam(0).toString();
            HttpServletRequest httpRequest = new HttpServletRequestImpl(command, query);
            HttpServletResponse httpResponse = new HttpServletResponseImpl();
            HandlerExecutionChain handlerExecutionChain = mappings.getHandler(httpRequest);
            if (handlerExecutionChain != null) {
                handlerAdapter.handle(httpRequest, httpResponse, handlerExecutionChain.getHandler());
                Object result = httpRequest.getAttribute(RespMvcReturnValueHandler.RESP_RESULT);
                if (result != null) {
                  return serializer.getValue(result);
                } else {
                    return error("no response");
                }
            } else {
                return error("mapping not found: " + query);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }
    }
}
