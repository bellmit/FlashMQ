package mq.flashmq.core.clients.flash.impl;
import mq.flashmq.core.clients.flash.BaseFlashMQClient;
import mq.flashmq.core.clients.redis.BaseRedisClient;
import mq.flashmq.core.clients.redis.impl.RedisClient;
import redis.clients.jedis.Jedis;

public
class FlashMQClient extends BaseFlashMQClient {

    private static RedisClient redisClient;

    public FlashMQClient(String host, String password, int port) {
        super(host, password, port);
        redisClient = new RedisClient(host, port, password);
    }
    public FlashMQClient(String host, int port) {
        super(host, null, port);
        redisClient = new RedisClient(host, port);
    }

    public static RedisClient getRedisClient() {
        return redisClient;
    }
}
