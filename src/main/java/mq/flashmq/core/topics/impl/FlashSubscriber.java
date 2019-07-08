package mq.flashmq.core.topics.impl;

import mq.flashmq.core.ThreadFactory;
import mq.flashmq.core.clients.flash.impl.FlashMQClient;
import mq.flashmq.core.clients.redis.impl.RedisClient;
import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.queues.FlashQueue;
import mq.flashmq.core.topics.BaseFlashSubscriber;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public
class FlashSubscriber<T> extends BaseFlashSubscriber<T> {

    private static final Logger LOG = Logger.getLogger(FlashSubscriber.class.getName());

    public FlashSubscriber(FlashQueue queue) {
        super(queue);
    }

    @Override
    public void onMessage(String channel, String message) {
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(message));
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object object = ois.readObject();

            if (!(object instanceof Packet)) {
                LOG.log(Level.SEVERE,
                        "You can only send object of stype Packet",
                        new Exception("Wrong data type provided"));
                return;
            }

            Packet<T> receivedPacket = (Packet<T>) object;
            getQueue().enqueue(receivedPacket, false);
            ois.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "IOException in SpringFlashSubscriber", e);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "ClassNotFoundException in SpringFlashSubscriber", e);
        }
    }


    public void subscribe() {
        ThreadFactory.getInstance().execute(() -> {
            Jedis resource = FlashMQClient.getRedisClient().getResource();
            resource.subscribe(this, getQueue().getName());
        });
    }

    public void unsubscribe() {
        this.unsubscribe();
    }

}
