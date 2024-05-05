/*
 * Copyright (c) 2018-2024, Antonio Gabriel Muñoz Conejo <me at tonivade dot es>
 * Distributed under the terms of the MIT License
 */
package com.github.tonivade.resp.mvc;

import static com.github.tonivade.resp.protocol.RedisToken.array;
import static com.github.tonivade.resp.protocol.RedisToken.string;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tonivade.resp.RespCallback;
import com.github.tonivade.resp.RespClient;
import com.github.tonivade.resp.protocol.RedisToken;

@SpringBootTest
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
    client.send(array(string("POST"), string("/books"), string("Foundation")));
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
