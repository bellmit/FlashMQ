package mq.flashmq.core.clients.redis.impl;

import mq.flashmq.core.clients.redis.BaseRedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public
class RedisClient extends BaseRedisClient {

    private JedisPool jedisPool;

    public RedisClient( String host
                        , int port )  {
        super( host, port );
    }

    public RedisClient( String host
                        , int port
                        , String password )  {
        super( host, port, password );
    }

    public void connectPool(  )  {
        this.jedisPool = new JedisPool(getHost(), getPort());
    }

    public Jedis getResource(  )  {
        Jedis jedisClient = this.jedisPool.getResource();

        if ( getPassword() != null ) {
            jedisClient.auth( getPassword() );
        }
        return jedisClient;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
