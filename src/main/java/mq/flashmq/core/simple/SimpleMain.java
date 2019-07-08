package mq.flashmq.core.simple;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.*;

/***
 * Example Test Class
 */
public class SimpleMain {

    private static final BlockingQueue<Runnable> BLOCKING_QUEUE = new LinkedBlockingQueue<>();
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static  JedisPoolConfig CONFIG = new JedisPoolConfig();
    private static  JedisPool POOL = new JedisPool(CONFIG, "localhost", 6379);

    public static void main(String[] args) {
        CONFIG.setMaxTotal(10);
        SimpleSubscriber simpleSubscriber = new SimpleSubscriber();
        SimplePublisher publisher = new SimplePublisher("test") {
            @Override
            public void publish(String message) {
                EXECUTOR_SERVICE.execute(() -> {
                    Jedis resource = getResource();
                    resource.publish(getChannel(), message);
                });
            }
        };

        Jedis subscriber = POOL.getResource();
        EXECUTOR_SERVICE.execute(() -> subscriber.subscribe(simpleSubscriber, "test"));
        publisher.publish("Hello World!");
    }

    static Jedis getResource() {
        return POOL.getResource();
    }

}
