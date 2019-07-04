package mq.flashmq.core.packets;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("packets")
public
class Packet<T> implements Serializable {

    @Id
    private UUID packetId;
    private T payload;

    public Packet( String queue
                   ,T payload )  {
        this.packetId = UUID.randomUUID();
        this.payload = payload;
    }

    public T getPayload(  )  {
        return payload;
    }

    public UUID getPacketId(  )  {
        return packetId;
    }
}
