package mq.flashmq.core.clients.flash;

public
class BaseFlashMQClient {

    private String host;
    private String password;
    private int port;

    public BaseFlashMQClient(String host,
                             String password,
                             int port) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public String getHost(  )  {
        return this.host;
    }

    public void setHost( String host )  {
        this.host = host;
    }

    public String getPassword(  )  {
        return this.password;
    }

    public void setPassword( String password )  {
        this.password = password;
    }

    public int getPort(  )  {
        return this.port;
    }

    public void setPort( int port )  {
        this.port = port;
    }

}
