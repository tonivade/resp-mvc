package com.github.tonivade.resp.mvc;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RespHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
         RequestMapping methodAnnotation = returnType.getMethodAnnotation(RequestMapping.class);
         return methodAnnotation != null && Arrays.asList(methodAnnotation.produces()).contains("application/resp");
    }


    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        nativeRequest.setAttribute("RESP_RESULT", returnValue);
    }
}
