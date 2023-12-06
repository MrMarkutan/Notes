package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.repos.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public Optional<Address> getAddressById(int id) {
        return addressRepository.findById(id);
    }

    public void deleteAddress(int id) {
        getAddressById(id).ifPresent(addressRepository::delete);
    }

    public Optional<Address> updateAddress(int id, Address address) {
        return getAddressById(id)
                .map(existingAddress -> existingAddress.update(address))
                .map(this::saveAddress);
    }


    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }
}
