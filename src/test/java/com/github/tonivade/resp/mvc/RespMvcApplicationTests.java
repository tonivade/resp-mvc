/*
 * Copyright (c) 2018, Antonio Gabriel Muñoz Conejo <antoniogmc at gmail dot com>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc;

import static com.github.tonivade.resp.protocol.RedisToken.array;
import static com.github.tonivade.resp.protocol.RedisToken.string;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.tonivade.resp.RespCallback;
import com.github.tonivade.resp.RespClient;
import com.github.tonivade.resp.protocol.RedisToken;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RespMvcApplicationTests implements RespCallback {

  private final RespClient client = new RespClient("localhost", 7081, this);
  
  private final BlockingQueue<RedisToken> queue = new ArrayBlockingQueue<>(1);

  @Test
  void contextLoads() throws InterruptedException {
    client.start();
    
    System.out.println(queue.take());
    
    client.stop();
  }

  @Override
  public void onConnect() {
    client.send(array(string("POST"), string("/books"), string("Fundation")));
  }

  @Override
  public void onDisconnect() {
    System.out.println("disconnected");
  }

  @Override
  public void onMessage(RedisToken token) {
    queue.offer(token);
  }
}
