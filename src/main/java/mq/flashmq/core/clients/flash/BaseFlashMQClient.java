package mq.flashmq.core.clients.flash;

import mq.flashmq.core.clients.redis.BaseRedisClient;

public
class BaseFlashMQClient {

    private BaseRedisClient redisClient;

    public BaseFlashMQClient( BaseRedisClient redisClient )  {
        this.redisClient = redisClient;
    }

    public BaseRedisClient getRedisClient(  )  {
        return redisClient;
    }

    public void setRedisClient( BaseRedisClient redisClient )  {
        this.redisClient = redisClient;
    }
}
