package mq.flashmq.core;

import mq.flashmq.core.clients.flash.impl.FlashMQClient;
import mq.flashmq.core.clients.redis.impl.RedisClient;
import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.queues.FlashQueue;
import mq.flashmq.core.topics.impl.FlashPublisher;
import mq.flashmq.core.topics.impl.FlashSubscriber;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        FlashMQClient flashMQClient = new FlashMQClient("localhost", 6379);

        flashMQClient.connect();
        FlashQueue<String> stringQueue = new FlashQueue<String>("test") {
            @Override
            public void onReceivedPacket(Packet<String> packet) {
                System.out.println("Received packet: " + packet.getPayload());
            }
        };

        FlashSubscriber<String> stringFlashSubscriber = new FlashSubscriber<>(stringQueue);

        FlashPublisher publisher = new FlashPublisher("test");

        publisher.publishPacket(new Packet<>("Hello World!"));
    }

}
