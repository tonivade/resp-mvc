package com.github.tonivade.resp.mvc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.tonivade.resp.IRedisCallback;
import com.github.tonivade.resp.RedisClient;
import com.github.tonivade.resp.protocol.RedisToken;

class SimpleRedisClient implements IRedisCallback {

    private RedisClient client;

    private AtomicBoolean connected = new AtomicBoolean(false);

    private BlockingQueue<RedisToken> responses = new ArrayBlockingQueue<>(1);

    public SimpleRedisClient(String host, int port) {
        this.client = new RedisClient(host, port, this);
    }

    public void start() {
        client.start();
    }

    public void stop() {
        client.stop();
    }

    @Override
    public void onConnect() {
        connected.set(true);
    }

    @Override
    public void onDisconnect() {
        connected.set(true);
    }

    @Override
    public void onMessage(RedisToken token) {
        responses.offer(token);
    }

    public RedisToken send(RedisToken request) throws InterruptedException {
        client.send(request);
        return responses.take();
    }

    public boolean isConnected() {
        return connected.get();
    }
}