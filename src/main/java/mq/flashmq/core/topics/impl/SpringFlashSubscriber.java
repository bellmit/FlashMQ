package mq.flashmq.core.topics.impl;

import mq.flashmq.core.packets.Packet;
import mq.flashmq.core.queues.FlashQueue;
import mq.flashmq.core.topics.BaseFlashSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public
class SpringFlashSubscriber<T> extends BaseFlashSubscriber<T> implements MessageListener {

    private static final Logger LOG = Logger.getLogger(SpringFlashSubscriber.class.getName());

    @Autowired
    private RedisMessageListenerContainer container;

    public SpringFlashSubscriber(FlashQueue<T> queue) {
        super(queue);
        container.addMessageListener(this, new ChannelTopic(queue.getName()));
    }

    public void onMessage( Message message, byte[] bytes )  {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
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

}
