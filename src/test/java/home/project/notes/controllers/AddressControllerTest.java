package home.project.notes.controllers;

import home.project.notes.data.Address;
import home.project.notes.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @Mock
    private Model model;

    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressController = new AddressController(addressService);
    }

    @Test
    void getAddressesShouldReturnCorrectViewNameAndAttributes() {
        String viewName = addressController.getAddresses(model);

        verify(model).addAttribute(eq("addresses"), any());
        assertEquals("address/list", viewName);
    }

    @Test
    void getAddressFormShouldReturnCorrectViewName() {
        String viewName = addressController.getAddressForm(model);

        assertEquals("address/create", viewName);
    }

    @Test
    void addAddressShouldRedirectToAddressDetails() {
        Address address = new Address();
        Address fakeAddress = new Address();
        fakeAddress.setId(1);
        when(addressService.saveAddress(address)).thenReturn(fakeAddress);

        String redirectURL = addressController.addAddress(address);

        assertEquals("redirect:/address/1", redirectURL);
    }

    @Test
    void getAddressWithValidIdShouldReturnCorrectViewNameAndAttributes() {
        int id = 1;
        when(addressService.getAddressById(id)).thenReturn(Optional.of(new Address()));

        String viewName = addressController.getAddress(id, model);

        verify(model).addAttribute(eq("address"), any());
        assertEquals("address/details", viewName);
    }

    @Test
    void getAddressWithInvalidIdShouldReturnNotFoundViewName() {
        int id = 1;
        when(addressService.getAddressById(id)).thenReturn(Optional.empty());

        String viewName = addressController.getAddress(id, model);

        assertEquals("notfound", viewName);
    }

    @Test
    void getUpdateAddressWithValidIdShouldReturnCorrectViewNameAndAttributes() {
        int id = 1;
        when(addressService.getAddressById(id)).thenReturn(Optional.of(new Address()));

        String viewName = addressController.getUpdateAddress(id, model);

        verify(model).addAttribute(eq("address"), any());
        assertEquals("address/edit", viewName);
    }

    @Test
    void getUpdateAddressWithInvalidIdShouldReturnNotFoundViewName() {
        int id = 1;
        when(addressService.getAddressById(id)).thenReturn(Optional.empty());

        String viewName = addressController.getUpdateAddress(id, model);

        assertEquals("notfound", viewName);
    }

    @Test
    void updateAddressWithValidIdShouldRedirectToAddressDetails() {
        int id = 1;
        Address address = new Address();
        Address fakeAddress = new Address();
        fakeAddress.setId(id);
        when(addressService.updateAddress(id, address)).thenReturn(Optional.of(fakeAddress));

        String redirectURL = addressController.updateAddress(id, address, model);

        assertEquals("redirect:/address/1", redirectURL);
    }

    @Test
    void updateAddressWithInvalidIdShouldReturnNotFoundViewName() {
        int id = 1;
        Address address = new Address();
        when(addressService.updateAddress(id, address)).thenReturn(Optional.empty());

        String viewName = addressController.updateAddress(id, address, model);

        assertEquals("notfound", viewName);
    }

    @Test
    void deleteAddressShouldRedirectToAddressList() {
        int id = 1;

        String redirectURL = addressController.deleteAddress(id);

        assertEquals("redirect:/address/list", redirectURL);
    }
}
