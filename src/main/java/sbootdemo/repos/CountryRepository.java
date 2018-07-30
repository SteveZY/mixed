package sbootdemo.repos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sbootdemo.domain.Country;
@Repository
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {

}
