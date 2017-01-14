package com.github.tonivade.resp.mvc;

import static java.util.Arrays.asList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RespMvcReturnValueHandler implements HandlerMethodReturnValueHandler {

    public static final String APPLICATION_RESP = "application/resp";
    public static final String RESP_RESULT = "RESP_RESULT";

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
         RequestMapping methodAnnotation = returnType.getMethodAnnotation(RequestMapping.class);
         return methodAnnotation != null && asList(methodAnnotation.produces()).contains(APPLICATION_RESP);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {
        HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        nativeRequest.setAttribute(RESP_RESULT, returnValue);
    }
}
