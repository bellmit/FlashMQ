package mq.flashmq.core.topics.impl;

import mq.flashmq.core.clients.redis.impl.RedisClient;
import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.topics.BaseFlashPublisher;

public class FlashPublisher extends BaseFlashPublisher {

    public FlashPublisher(String queue) {
        super(queue);
    }

    public void publishPacket( Packet packet )  {
       if (RedisClient.getJedisClient() != null && RedisClient.getJedisClient().isConnected()) {
           RedisClient.getJedisClient().publish(getQueue().getBytes(), packet.toByteArray());
       }
    }

}
