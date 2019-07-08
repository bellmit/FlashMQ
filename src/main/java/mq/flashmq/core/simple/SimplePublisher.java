package mq.flashmq.core.simple;

/***
 * Example Test Class
 */
public abstract class SimplePublisher {

    private String channel;

    public SimplePublisher(String channel) {
        this.channel = channel;
    }

    public abstract void publish(final String message);

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
