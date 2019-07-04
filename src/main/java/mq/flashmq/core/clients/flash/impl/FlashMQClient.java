package mq.flashmq.core.clients.flash.impl;
import mq.flashmq.core.clients.flash.BaseFlashMQClient;
import mq.flashmq.core.clients.redis.impl.RedisClient;

public
class FlashMQClient extends BaseFlashMQClient {

    public FlashMQClient( RedisClient redisClient )  {
        super(redisClient);
    }

    public RedisClient getRedisClient(  )  {
        return ( RedisClient ) super.getRedisClient();
    }
}
