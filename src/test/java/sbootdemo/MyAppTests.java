package sbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.junit4.SpringRunner;
import sbootdemo.config.MyConfigProps;

import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MyAppTests {
    @Autowired
    MyConfigProps myConfigProps;
    Resource resource;

    @Before
    public void setup() {
        resource = new ClassPathResource("/test.props");
    }

//    @Test
    public void contextLoads() {
        log.info("the props are {} ", myConfigProps);
    }

//    @Test
    public void propTest() {
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}