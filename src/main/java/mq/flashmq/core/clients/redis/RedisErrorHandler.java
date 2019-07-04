package mq.flashmq.core.clients.redis;

import org.springframework.util.ErrorHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public
class RedisErrorHandler implements ErrorHandler {

    private static final Logger LOG = Logger.getLogger(RedisErrorHandler.class.getName());

    public void handleError(Throwable throwable) {
        LOG.log(Level.SEVERE, "Redis error", throwable);
    }
}
