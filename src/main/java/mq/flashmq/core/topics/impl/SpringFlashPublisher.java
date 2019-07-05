package mq.flashmq.core.topics.impl;

import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.topics.BaseFlashPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

public class SpringFlashPublisher extends BaseFlashPublisher {

    @Autowired
    private RedisTemplate<String, Packet> template;

    public SpringFlashPublisher(String queue)  {
        super(queue);
    }

    public void publishPacket( Packet packet )  {
        template.convertAndSend(getQueue(), packet);
    }

}
