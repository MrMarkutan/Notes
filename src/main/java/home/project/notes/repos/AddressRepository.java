package home.project.notes.repos;

import home.project.notes.data.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE " +
            "(:country IS NULL OR a.country = :country) AND " +
            "(:city IS NULL OR a.city = :city) AND " +
            "(:street IS NULL OR a.street = :street) AND " +
            "(:building IS NULL OR a.building = :building) AND " +
            "(:apartment IS NULL OR a.apartment = :apartment)")
    List<Address> findByCountryAndCityAndStreetAndBuildingAndApartment(
            @Param("country") String country,
            @Param("city") String city,
            @Param("street") String street,
            @Param("building") Integer building,
            @Param("apartment") Integer apartment);
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