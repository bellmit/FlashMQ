package mq.flashmq.core.topics;

import mq.flashmq.core.queues.FlashQueue;

public
class BaseFlashSubscriber<T> {

    private FlashQueue<T> queue;

    public BaseFlashSubscriber( FlashQueue<T> queue )  {
        this.queue = queue;
    }

    public FlashQueue<T> getQueue(  )  {
        return queue;
    }

}
