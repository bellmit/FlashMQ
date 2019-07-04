package mq.flashmq.core.queues;

import mq.flashmq.core.packets.Packet;

import java.util.LinkedList;
import java.util.Queue;

public
abstract class FlashQueue<T> {

    private String name;
    private Queue<Packet<T>> queue;

    public FlashQueue( String name )  {
        this.name = name;
        this.queue = new LinkedList<Packet<T>>();
    }

    public abstract void onReceivedPacket(Packet<T> packet);

    public Packet<T> poll(  )  {
        return queue.poll();
    }

    public Packet<T> peek(  )  {
        return queue.peek();
    }

    public void enqueue( Packet<T> packet )  {
        this.queue.add(packet);
        this.onReceivedPacket(packet);
    }

    public void clearQueue(  )  {
        this.queue.clear();
    }

    public int getSize(  )  {
        return this.queue.size();
    }

    public String getName(  )  {
        return name;
    }

    public void setName( String name )  {
        this.name = name;
    }
}
