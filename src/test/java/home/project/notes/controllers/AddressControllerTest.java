package home.project.notes.controllers;

import home.project.notes.data.Address;
import home.project.notes.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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
    void getAddressWithIdShouldReturnCorrectViewNameAndAttributes() {
        int id = 1;
        when(addressService.findAddressById(id)).thenReturn(new Address());

        String viewName = addressController.getAddress(id, model);

        verify(model).addAttribute(eq("address"), any());
        assertEquals("address/details", viewName);
    }

    @Test
    void getUpdateAddressWithIdShouldReturnCorrectViewNameAndAttributes() {
        int id = 1;
        when(addressService.findAddressById(id)).thenReturn(new Address());

        String viewName = addressController.getUpdateAddress(id, model);

        verify(model).addAttribute(eq("address"), any());
        assertEquals("address/edit", viewName);
    }


    @Test
    void updateAddressWithValidIdShouldRedirectToAddressDetails() {
        int id = 1;
        Address address = new Address();
        Address fakeAddress = new Address();
        fakeAddress.setId(id);
        when(addressService.updateAddress(id, address)).thenReturn(fakeAddress);

        String redirectURL = addressController.updateAddress(id, address, model);

        assertEquals("redirect:/address/1", redirectURL);
    }


    @Test
    void deleteAddressShouldRedirectToAddressList() {
        int id = 1;

        String redirectURL = addressController.deleteAddress(id);

        assertEquals("redirect:/address/list", redirectURL);
    }
}
