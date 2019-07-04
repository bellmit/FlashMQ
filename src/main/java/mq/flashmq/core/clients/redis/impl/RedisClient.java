package mq.flashmq.core.clients.redis.impl;

import mq.flashmq.core.clients.redis.BaseRedisClient;
import redis.clients.jedis.Jedis;

public
class RedisClient extends BaseRedisClient {

    private Jedis jedisClient;

    public RedisClient( String host
                        , int port )  {
        super( host, port );
    }

    public RedisClient( String host
                        , int port
                        , String password )  {
        super( host, port, password );
    }

    public void connect(  )  {
        this.jedisClient = new Jedis( getHost(), getPort() );

        if ( getPassword() != null ) {
            this.jedisClient.auth( getPassword() );
        }
        this.jedisClient.connect();
    }

}
