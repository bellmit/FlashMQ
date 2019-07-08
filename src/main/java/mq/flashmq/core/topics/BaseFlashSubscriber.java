package mq.flashmq.core.topics;

import mq.flashmq.core.queues.FlashQueue;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public
class BaseFlashSubscriber<T> extends JedisPubSub {

    private FlashQueue<T> queue;

    public BaseFlashSubscriber(FlashQueue<T> queue)  {
        this.queue = queue;
    }

    public FlashQueue<T> getQueue(  )  {
        return queue;
    }

}
