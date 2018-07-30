package sbootdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my.datasource")
@Data
public class DataSourceProps {
    private String driverClassName;
    private String url;
    private String username;
    private String password = "";

}
