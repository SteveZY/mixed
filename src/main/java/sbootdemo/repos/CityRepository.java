package sbootdemo.repos;

import org.springframework.data.repository.*;
import sbootdemo.domain.City;
import sbootdemo.domain.Country;

import java.util.List;

@org.springframework.stereotype.Repository
public interface CityRepository extends PagingAndSortingRepository<City, Long> {

    List<City> findByCountry(Country country);
    City findByNameAndStateAllIgnoringCase(String name, String state);

}