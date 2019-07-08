package mq.flashmq.core.packets;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.*;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RedisHash("packets")
public
class Packet<T> implements Serializable {

    private static final Logger LOG = Logger.getLogger(Packet.class.getName());

    @Id
    private UUID packetId;
    private T payload;

    public Packet(T payload )  {
        this.packetId = UUID.randomUUID();
        this.payload = payload;
    }

    public T getPayload(  )  {
        return payload;
    }

    public UUID getPacketId(  )  {
        return packetId;
    }

    public byte[] toByteArray() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(baos);

            outputStream.writeObject(this);
            return baos.toByteArray();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Array converting packet to byte[]", e);
        }
        return new byte[] {  };
    }


    @Override
    public String toString() {
        return Base64.getEncoder().encodeToString(this.toByteArray());
    }
}
