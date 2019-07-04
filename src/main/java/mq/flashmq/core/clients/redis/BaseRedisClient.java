package mq.flashmq.core.clients.redis;

public
class BaseRedisClient {

    private String host;
    private String password;
    private int port;

    public BaseRedisClient( String host
                            , int port ) {
        this( host, port, null );
    }

    public BaseRedisClient( String host
                            , int port
                            , String password ) {
        this.host = host;
        this.port = port;
        this.password = password;
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

    public String getPassword(  )  {
        return this.password;
    }

    public void setPassword( String password )  {
        this.password = password;
    }
}
