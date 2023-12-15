package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.exception.ResourceNotFoundException;
import home.project.notes.repos.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address saveAddress(Address address) {
        Address saved;
        String country = address.getCountry();
        String city = address.getCity();
        String street = address.getStreet();
        Integer building = address.getBuilding();
        Integer apartment = address.getApartment();

        if (addressRepository.existsByCountryEqualsAndCityAndStreetAndBuildingAndApartment(
                country,
                city,
                street,
                building,
                apartment)
        ) {
            saved = addressRepository.findByCountryEqualsAndCityAndStreetAndBuildingAndApartment(
                    country,
                    city,
                    street,
                    building,
                    apartment);
        } else {
            saved = addressRepository.save(address);
        }
        return saved;
    }


    public Address findAddressById(int id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Address with ID: %d was not found.", id)));
    }

    public void deleteAddress(int id) {
        addressRepository.delete(findAddressById(id));
    }

    public Address updateAddress(int id, Address address) {
        Address updated = findAddressById(id).update(address);
        return saveAddress(updated);
    }


    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> searchAddresses(String country,
                                         String city,
                                         String street,
                                         Integer building,
                                         Integer apartment) {
        return addressRepository
                .findByCountryAndCityAndStreetAndBuildingAndApartment(country, city, street, building, apartment);
    }
}
