package com.sakura.usercenter.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Configuration
@ConditionalOnClass(ObjectMapper.class)
public class JsonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            // 定义日期时间的格式化器
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            jacksonObjectMapperBuilder.timeZone("GMT+8");

            // 添加JavaTimeModule以支持Java 8日期时间类型
            jacksonObjectMapperBuilder.modules(new JavaTimeModule());
            // 配置日期时间格式
            jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            jacksonObjectMapperBuilder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
            jacksonObjectMapperBuilder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
            // 对Long和BigInteger类型使用ToStringSerializer，以避免精度丢失
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        };
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.createXmlMapper(false).build().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
