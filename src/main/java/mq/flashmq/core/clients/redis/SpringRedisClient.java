package mq.flashmq.core.clients.redis;

import org.springframework.stereotype.Service;

@Service
public class SpringRedisClient extends RedisClient {

    public SpringRedisClient( String host
                            , int port )  {
        super( host, port );
    }

    public SpringRedisClient( String host
                            , int port
                            , String password )  {
        super( host, port, null );
    }

}
