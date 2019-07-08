package mq.flashmq.core.clients.redis.impl;

import mq.flashmq.core.clients.redis.BaseRedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public
class RedisClient extends BaseRedisClient {

    private static JedisPoolConfig CONFIG = new JedisPoolConfig();
    private static JedisPool POOL = new JedisPool();

    static {
        CONFIG.setMaxTotal(30);
    }

    public RedisClient(String host, int port) {
        super(host, port);
        POOL = new JedisPool(CONFIG, host, port);
    }

    public RedisClient(String host, int port, String password) {
        super(host, port, password);
        POOL = new JedisPool(CONFIG, host, port);
    }

    public Jedis getResource() {

        Jedis resource = POOL.getResource();

        if (getPassword() != null) {
            resource.auth(getPassword());
        }

        return resource;
    }



}
