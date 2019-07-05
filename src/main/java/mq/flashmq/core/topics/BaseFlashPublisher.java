package mq.flashmq.core.topics;

import redis.clients.jedis.Jedis;

public
class BaseFlashPublisher {

    private String queue;

    public BaseFlashPublisher( String queue)  {
        this.queue = queue;
    }

    public String getQueue(  )  {
        return queue;
    }

    public void setQueue(String queue )  {
        this.queue = queue;
    }

}
