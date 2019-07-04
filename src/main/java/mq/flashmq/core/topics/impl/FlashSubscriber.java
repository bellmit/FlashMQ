package mq.flashmq.core.topics.impl;

import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.queues.FlashQueue;
import mq.flashmq.core.topics.BaseFlashSubscriber;
import redis.clients.jedis.JedisPubSub;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlashSubscriber<T> extends BaseFlashSubscriber<T>  {

    private static final Logger LOG = Logger.getLogger(FlashSubscriber.class.getName());

    private JedisPubSub jedisPubSub;

    public FlashSubscriber(FlashQueue queue) {
        super(queue);

        this.jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                ByteArrayInputStream bais = new ByteArrayInputStream(message.getBytes());
                try {
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    Object object = ois.readObject();

                    if (!(object instanceof Packet)) {
                        LOG.log(Level.SEVERE, "You can only send object of stype Packet", new Exception("Wrong data type provided"));
                        return;
                    }

                    Packet<T> receivedPacket = ( Packet<T> ) ois.readObject();
                    getQueue().enqueue( receivedPacket );
                } catch (IOException e) {
                    LOG.log( Level.SEVERE, "IOException in SpringFlashSubscriber", e );
                } catch (ClassNotFoundException e) {
                    LOG.log( Level.SEVERE, "ClassNotFoundException in SpringFlashSubscriber", e );
                }
            }
        };

        this.jedisPubSub.subscribe(queue.getName());
    }

    public JedisPubSub getJedisPubSub(  )  {
        return jedisPubSub;
    }
}
