package mq.flashmq.core.clients.flash.impl;
import mq.flashmq.core.clients.flash.BaseFlashMQClient;
import mq.flashmq.core.clients.redis.impl.RedisClient;

public
class FlashMQClient extends BaseFlashMQClient {

    private RedisClient redisClient;

    public FlashMQClient(String host, String password, int port) {
        super(host, password, port);
        this.redisClient = new RedisClient(host, port, password);
    }

    public void connect() {
        this.redisClient.connect();
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }
}
