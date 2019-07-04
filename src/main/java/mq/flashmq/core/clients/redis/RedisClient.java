package mq.flashmq.core.clients.redis;

public class RedisClient extends BaseRedisClient {

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
        this.getJedisClient().connect();
    }

}
