package mq.flashmq.core.topics.impl;

import mq.flashmq.core.clients.redis.impl.RedisClient;
import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.queues.FlashQueue;
import mq.flashmq.core.topics.BaseFlashSubscriber;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public
class FlashSubscriber<T> extends BaseFlashSubscriber<T>  {

    private static final Logger LOG = Logger.getLogger(FlashSubscriber.class.getName());

    private static final Jedis SUBSCRIBER = new Jedis("localhost");

    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingQueue<>();
    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, BLOCKING_QUEUE);

    private JedisPubSub jedisPubSub;

    public FlashSubscriber(FlashQueue queue) {
        super(queue);

        this.jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(message));
                try {
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Object object = ois.readObject();

                    if (!(object instanceof Packet))  {
                        LOG.log(Level.SEVERE,
                                "You can only send object of stype Packet",
                                new Exception("Wrong data type provided"));
                        return;
                    }

                    Packet<T> receivedPacket = ( Packet<T> ) ois.readObject();
                    getQueue().enqueue( receivedPacket, false );
                } catch (IOException e) {
                    LOG.log( Level.SEVERE, "IOException in SpringFlashSubscriber", e );
                } catch (ClassNotFoundException e) {
                    LOG.log( Level.SEVERE, "ClassNotFoundException in SpringFlashSubscriber", e );
                }
            }
        };
        this.subscribe();
    }

    private void subscribe() {
        EXECUTOR_SERVICE.execute(() -> {
            SUBSCRIBER.subscribe(jedisPubSub, getQueue().getName());
        });
    }

    public void unsubscribe() {
       jedisPubSub.unsubscribe();
    }

    public JedisPubSub getJedisPubSub( )  {
        return jedisPubSub;
    }
}
