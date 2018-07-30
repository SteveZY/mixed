package sbootdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Data
public class MyConfigProps {
    private String town;
    private String whatTosay;
    private String firstname;
    private String surname;
    private String fullname;//=${firstname} ${surname}
    private String lastvisit;//=1/1/2010
    private String welcome;//=Hi ${fullname} Thanks for visiting on ${lastvisit}
    private HibernateProps hibernate;

//    public String getTown() {
//        return town;
//    }
//
//    public void setTown(String town) {
//        this.town = town;
//    }
//
//
//    public String getWhatTosay() {
//        return whatTosay;
//    }

//    public void setWhatTosay(String whatTosay) {
//        this.whatTosay = whatTosay;
//    }
    //    @JsonPOJOBuilder(withPrefix = "")
//    public static final class Builder {
//
//    }
}
