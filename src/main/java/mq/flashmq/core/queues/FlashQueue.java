package mq.flashmq.core.queues;

import mq.flashmq.core.packets.Packet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

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

    public void enqueue( Packet<T> packet, boolean deleteOrder )  {
        this.queue.add(packet);
        this.onReceivedPacket(packet);

        if ( deleteOrder )  {
            removePacketById( packet.getPacketId() );
        }
    }

    public void removePacketById(UUID uuid) {
        Iterator<Packet<T>> it = queue.iterator();
        while(it.hasNext()) {
            Packet p = it.next();
            if (p.getPacketId().equals(uuid)) {
                it.remove();
                break;
            }
        }
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
