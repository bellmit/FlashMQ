package mq.flashmq.core.simple;

import redis.clients.jedis.JedisPubSub;

/***
 * Example Test Class
 */
public class SimpleSubscriber extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("Message from channel: " + channel);
        System.out.println("-> " + message + System.lineSeparator());
    }

}
