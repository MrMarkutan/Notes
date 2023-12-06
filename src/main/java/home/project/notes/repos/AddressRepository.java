package home.project.notes.repos;

import home.project.notes.data.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findByCountryEqualsAndCityAndStreetAndBuildingAndApartment(String country,
                                                                       String city,
                                                                       String street,
                                                                       Integer building,
                                                                       Integer apartment);

    boolean existsByCountryEqualsAndCityAndStreetAndBuildingAndApartment(String country,
                                                                         String city,
                                                                         String street,
                                                                         Integer building,
                                                                         Integer apartment);
}