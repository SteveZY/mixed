package sbootdemo.config;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
@ConfigurationProperties
@Data
public class HibernateProps {
    private String dialect;
    private boolean showSql;
    private boolean formatSql;
    private String hbm2ddlAuto;
}
