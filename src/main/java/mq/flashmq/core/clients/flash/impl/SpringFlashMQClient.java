package mq.flashmq.core.clients.flash.impl;

import mq.flashmq.core.clients.flash.BaseFlashMQClient;
import mq.flashmq.core.clients.redis.impl.SpringRedisConfig;
import org.springframework.stereotype.Service;

@Service
public
class SpringFlashMQClient extends BaseFlashMQClient {

    public SpringFlashMQClient(SpringRedisConfig redisClient)  {
        super(redisClient);
    }

    public SpringRedisConfig getRedisClient(  )  {
        return (SpringRedisConfig) super.getRedisClient();
    }
}
