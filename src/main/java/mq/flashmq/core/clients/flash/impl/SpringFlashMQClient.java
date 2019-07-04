package mq.flashmq.core.clients.flash.impl;

import mq.flashmq.core.clients.flash.BaseFlashMQClient;
import mq.flashmq.core.clients.redis.impl.SpringRedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public
class SpringFlashMQClient extends BaseFlashMQClient {

    public SpringFlashMQClient(String host, String password, int port) {
        super(host, password, port);
    }

    @Bean
    public SpringRedisConfig getRedisConfig() {
        return new SpringRedisConfig(getHost(), getPort(), getPassword());
    }

}
