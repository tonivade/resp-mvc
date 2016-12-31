package com.github.tonivade.resp.mvc;

import static com.github.tonivade.resp.protocol.RedisToken.array;
import static com.github.tonivade.resp.protocol.RedisToken.string;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tonivade.resp.protocol.RedisToken;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RespMvcApplicationTests {

    @Value("${demo.host}")
    private String host;
    @Value("${demo.port}")
    private int port;

    private SimpleRedisClient client;

    @Before
    public void setUp() {
        client = new SimpleRedisClient(host, port);
        client.start();
    }

    @After
    public void tearDown() {
        client.stop();
    }

    @Test
    public void contextLoads() throws Exception {
        RedisToken response = client.send(array(string("GET"), string("/user/agmunoz")));

        assertThat(response, equalTo(array(string("id"), string("agmunoz"),string("name"), string("Antonio Muñoz"))));
    }
}
