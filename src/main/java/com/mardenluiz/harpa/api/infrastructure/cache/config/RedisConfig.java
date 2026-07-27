package com.mardenluiz.harpa.api.infrastructure.cache.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mardenluiz.harpa.api.web.dto.AudioDto;
import com.mardenluiz.harpa.api.web.dto.HymnDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Bean
    public ObjectMapper redisObjectMapper() {

        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public RedisTemplate<String, HymnDto> hymnRedisTemplate(RedisConnectionFactory connectionFactory,
                                                            ObjectMapper redisObjectMapper) {

        RedisTemplate<String, HymnDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<HymnDto> valueSerializer =
                new Jackson2JsonRedisSerializer<>(redisObjectMapper, HymnDto.class);


        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisTemplate<String, AudioDto> audioRedisTemplate(RedisConnectionFactory connectionFactory,
                                                              ObjectMapper redisObjectMapper) {

        RedisTemplate<String, AudioDto> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        StringRedisSerializer keySerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<AudioDto> valueSerializer =
                new Jackson2JsonRedisSerializer<>(redisObjectMapper, AudioDto.class);


        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();

        return template;
    }

}