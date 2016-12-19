package com.github.tonivade.resp.mvc;

import java.io.IOException;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.github.tonivade.resp.mvc.command.RespSerializer;
import com.github.tonivade.resp.protocol.RedisSerializer;

public class RespMessageConverter extends AbstractHttpMessageConverter<Object> {

  private static final String APPLICATION_RESP = "application/resp";

  private final RedisSerializer serializer = new RedisSerializer();
  private final RespSerializer respSerializer = new RespSerializer();

  public RespMessageConverter() {
    super(MediaType.parseMediaType(APPLICATION_RESP));
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void writeInternal(Object object, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    outputMessage.getBody().write(serializer.encodeToken(respSerializer.getValue(object)));
  }

}
