package mq.flashmq.core.clients.redis;

import redis.clients.jedis.Jedis;

public class BaseRedisClient {

    private String host;
    private int port;

    private Jedis jedisClient;

    public BaseRedisClient( String host
                            , int port ) {
        this( host, port, null );
    }

    public BaseRedisClient( String host
                            , int port
                            , String password ) {
        this.host = host;
        this.port = port;
        this.jedisClient = new Jedis( this.host, this.port );

        if ( password != null ) {
            this.jedisClient.auth( password );
        }
    }

    public String getHost(  )  {
        return host;
    }

    public void setHost( String host )  {
        this.host = host;
    }

    public int getPort(  )  {
        return port;
    }

    public void setPort( int port )  {
        this.port = port;
    }

    public Jedis getJedisClient(  )  {
        return jedisClient;
    }

    public void setJedisClient( Jedis jedisClient )  {
        this.jedisClient = jedisClient;
    }

}
