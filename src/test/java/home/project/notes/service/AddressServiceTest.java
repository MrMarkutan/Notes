package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.exception.ResourceNotFoundException;
import home.project.notes.repos.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressService = new AddressService(addressRepository);
    }

    @Test
    void saveAddressWithNonExistingAddressShouldSaveAndReturnAddress() {
        Address addressToSave = new Address();
        when(addressRepository.existsByCountryEqualsAndCityAndStreetAndBuildingAndApartment(
                any(), any(), any(), any(), any())).thenReturn(false);
        when(addressRepository.save(addressToSave)).thenReturn(addressToSave);

        Address savedAddress = addressService.saveAddress(addressToSave);

        assertEquals(addressToSave, savedAddress);
    }

    @Test
    void saveAddressWithExistingAddressShouldReturnExistingAddress() {
        Address existingAddress = new Address();
        when(addressRepository.existsByCountryEqualsAndCityAndStreetAndBuildingAndApartment(
                any(), any(), any(), any(), any())).thenReturn(true);
        when(addressRepository.findByCountryEqualsAndCityAndStreetAndBuildingAndApartment(
                any(), any(), any(), any(), any())).thenReturn(existingAddress);

        Address savedAddress = addressService.saveAddress(new Address());

        assertEquals(existingAddress, savedAddress);
    }

    @Test
    void getAddressByIdWithValidIdShouldReturnAddress() {
        int id = 1;
        Address expectedAddress = new Address();
        when(addressRepository.findById(id)).thenReturn(Optional.of(expectedAddress));

        Address actualAddress = addressService.findAddressById(id);

        assertEquals(expectedAddress, actualAddress);
    }

    @Test
    void getAddressByIdWithInvalidIdShouldThrow() {
        int id = 1;
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.findAddressById(id));

        verify(addressRepository, times(1)).findById(id);

    }

    @Test
    void deleteAddressWithValidIdShouldDeleteAddress() {
        int id = 1;
        Address addressToDelete = new Address();
        when(addressRepository.findById(id)).thenReturn(Optional.of(addressToDelete));

        addressService.deleteAddress(id);

        verify(addressRepository, times(1)).delete(addressToDelete);
    }

    @Test
    void updateAddressWithValidIdShouldUpdateAddress() {
        int id = 1;
        Address existingAddress = new Address();
        Address newAddress = new Address();
        when(addressRepository.findById(id)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(existingAddress)).thenReturn(existingAddress);

        Address updatedAddress = addressService.updateAddress(id, newAddress);

        assertEquals(existingAddress, updatedAddress);
    }

    @Test
    void updateAddressWithInvalidIdShouldReturnEmptyOptional() {
        int id = 1;
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(id, new Address()));

        verify(addressRepository, times(1)).findById(id);
    }

    @Test
    void getAddressesShouldReturnAddresses() {
        List<Address> expectedAddresses = Collections.singletonList(new Address());
        when(addressRepository.findAll()).thenReturn(expectedAddresses);

        List<Address> actualAddresses = addressService.getAddresses();

        assertEquals(expectedAddresses, actualAddresses);
    }

    @Test
    void searchAddressesWithSearchParamsShouldReturnMatchingAddresses() {
        String country = "USA";
        String city = "New York";
        String street = "Broadway";
        Integer building = 123;
        Integer apartment = 456;

        List<Address> expectedAddresses = Collections.singletonList(new Address());
        when(addressRepository.findByCountryAndCityAndStreetAndBuildingAndApartment(
                country, city, street, building, apartment)).thenReturn(expectedAddresses);

        List<Address> actualAddresses = addressService.searchAddresses(country, city, street, building, apartment);

        assertEquals(expectedAddresses, actualAddresses);
    }
}
