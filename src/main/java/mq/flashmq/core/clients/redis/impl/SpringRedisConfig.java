package mq.flashmq.core.clients.redis.impl;

import mq.flashmq.core.packets.Packet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public
class SpringRedisConfig extends RedisClient {

    public SpringRedisConfig(String host
                            , int port )  {
        super( host, port );
    }

    public SpringRedisConfig(String host
                            , int port
                            , String password )  {
        super( host, port, null );
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(  )  {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(getHost());
        standaloneConfiguration.setPort(getPort());
        if (getPassword() != null) {
            standaloneConfiguration.setPassword(
                    RedisPassword.of(getPassword()));
        } else {
            standaloneConfiguration.setPassword(
                    RedisPassword.none());
        }
        return new JedisConnectionFactory(standaloneConfiguration);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer redisMessageListenerContainer =
                                        new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory());
        return redisMessageListenerContainer;
    }

    @Bean
    public RedisTemplate<String, Packet> redisTemplate() {
        RedisTemplate<String, Packet> template = new RedisTemplate<String, Packet>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


}
