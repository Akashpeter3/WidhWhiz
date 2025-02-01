package com.app.apigateway.api_gateway.client;

import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }
    @Bean
    public Decoder feignDecoder() {
        return new SpringDecoder(() -> new HttpMessageConverters(new MappingJackson2HttpMessageConverter()));
    }
}
