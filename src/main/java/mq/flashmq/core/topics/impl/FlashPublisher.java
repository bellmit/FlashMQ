package mq.flashmq.core.topics.impl;

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

    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingQueue<>();
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, BLOCKING_QUEUE);

    public FlashPublisher(String queue) {
        super(queue);
    }

    public void publishPacket(Packet packet) {
        EXECUTOR_SERVICE.execute(() -> {
            Jedis jedis = FlashMQClient.getRedisClient().getResource();
            if (jedis != null) {
                try {
                    jedis.publish(nameToByteString(), packet.toString());
                } catch (Exception e) {
                    LOG.log(Level.SEVERE,"Error publishing packet", e);
                } finally {
                    FlashMQClient.getRedisClient().getJedisPool().returnResource(jedis);
                }
            }
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

    public void close() {
        EXECUTOR_SERVICE.shutdown();
    }

}
