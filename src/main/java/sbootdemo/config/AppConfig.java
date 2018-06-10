package sbootdemo.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ComponentScan
@Configuration
@EnableConfigurationProperties({MyConfigProps.class})
@Slf4j
public class AppConfig {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MyConfigProps myConfigProps;
    @PostConstruct
    public void constructed(){
        try {
            objectMapper.writeValueAsString("xxxx");
        } catch (JsonProcessingException e) {

            log.error("exception rcvd");
        }
        try {
            log.info("The props are {}",objectMapper.writeValueAsString(myConfigProps));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
