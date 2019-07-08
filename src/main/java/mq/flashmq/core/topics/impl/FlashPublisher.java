package mq.flashmq.core.topics.impl;

import mq.flashmq.core.ThreadFactory;
import mq.flashmq.core.clients.flash.impl.FlashMQClient;
import mq.flashmq.core.clients.redis.impl.RedisClient;
import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.topics.BaseFlashPublisher;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlashPublisher extends BaseFlashPublisher {

    private static final Logger LOG = Logger.getLogger(FlashPublisher.class.getName());

    public FlashPublisher(String queue) {
        super(queue);
    }

    public void publishPacket(Packet packet) {
        ThreadFactory.getInstance().execute(() -> {
            Jedis jedis = FlashMQClient.getRedisClient().getResource();
            jedis.publish(getQueue(), packet.toString());
        });
    }


    public String nameToByteString() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(baos);

            outputStream.writeBytes(getQueue());
            outputStream.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Array converting packet to byte[]", e);
        }
        return Base64.getEncoder().encodeToString(new byte[]{});
    }

}
