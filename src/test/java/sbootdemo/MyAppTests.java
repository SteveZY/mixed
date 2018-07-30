package sbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.junit4.SpringRunner;
import sbootdemo.config.MyConfigProps;
import sbootdemo.domain.City;
import sbootdemo.domain.Country;
import sbootdemo.repos.CityRepository;
import sbootdemo.repos.CountryRepository;

import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MyAppTests {
    @Autowired
    MyConfigProps myConfigProps;
    Resource resource;

    @Autowired
    DataSource ds;

    @Autowired
    CityRepository cityRepository;
    @Autowired
    CountryRepository countryRepository;

    @Before
    public void setup() {
        resource = new ClassPathResource("/test.props");
    }

    @Test
    public void contextLoads() throws SQLException {
        log.info("the props are {} ", myConfigProps);
        log.info("the dataSource are {} ", ds.getConnection().getSchema());
        Country country = Country.builder().name("countryName" + 11).flag("flag" + 11).build();
//        countryRepositr
        Country ddd = countryRepository.save(country);
        for (int i = 0; i < 2; i++) {

            cityRepository.save(/*new City("name" + i, "state" + i)*/
                    City.builder().name("cityname"+i).state("state"+i)
                            .country(ddd).build());
        }
        Iterable<City> cities = cityRepository.findAll();
        cities.forEach(System.out::println);
    }

    @Test
    public void propTest() {
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}