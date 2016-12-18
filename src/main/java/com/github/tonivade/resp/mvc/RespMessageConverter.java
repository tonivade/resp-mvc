package com.github.tonivade.resp.mvc;

import static java.lang.String.valueOf;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.github.tonivade.resp.protocol.RedisSerializer;
import com.github.tonivade.resp.protocol.RedisToken;

public class RespMessageConverter extends AbstractHttpMessageConverter<Object> {

  private static final String APPLICATION_RESP = "application/resp";

  private final RedisSerializer serializer = new RedisSerializer();

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
    outputMessage.getBody().write(serializer.encodeToken(getValue(object)));
  }

  private RedisToken getValue(Object object) {
    if (object.getClass().isPrimitive()) {
      return RedisToken.string(valueOf(object));
    }
    if (String.class.isInstance(object)) {
      return RedisToken.string(valueOf(object));
    }
    if (Number.class.isInstance(object)) {
      return RedisToken.string(valueOf(object));
    }
    if (object.getClass().isArray()) {
      return getArrayValue(Object[].class.cast(object));
    }
    if (Collection.class.isInstance(object)) {
      return getCollectionValue(Collection.class.cast(object));
    }
    if (Map.class.isInstance(object)) {
      return getMapValue(Map.class.cast(object));
    }
    return getObjectValue(object);
  }

  private RedisToken getMapValue(Map<?, ?> map) {
    List<RedisToken> tokens = new ArrayList<>();
    for (Map.Entry<?, ?> entry : map.entrySet()) {
      tokens.add(getValue(entry.getKey()));
      tokens.add(getValue(entry.getValue()));
    }
    return RedisToken.array(tokens);
  }

  private RedisToken getCollectionValue(Collection<?> collection) {
    List<RedisToken> tokens = new ArrayList<>();
    for (Object item : collection) {
      tokens.add(getValue(item));
    }
    return RedisToken.array(tokens);
  }

  private RedisToken getArrayValue(Object[] array) {
    List<RedisToken> tokens = new ArrayList<>();
    for (Object item : array) {
      tokens.add(getValue(item));
    }
    return RedisToken.array(tokens);
  }

  private RedisToken getObjectValue(Object object) {
    List<RedisToken> tokens = new ArrayList<>();
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field field : fields) {
      try {
        tokens.add(RedisToken.string(field.getName()));
        field.setAccessible(true);
        tokens.add(getValue(field.get(object)));
      } catch (IllegalArgumentException | IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return RedisToken.array(tokens);
  }
}
