package home.project.notes.controllers;

import home.project.notes.data.Contact;
import home.project.notes.service.AddressService;
import home.project.notes.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @Mock
    private AddressService addressService;

    @Mock
    private Model model;

    private ContactController contactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contactController = new ContactController(contactService, addressService);
    }

    @Test
    void getAllContactsShouldReturnCorrectViewNameAndAttributes() {
        String viewName = contactController.getAllContacts(model);
        verify(model).addAttribute(eq("contacts"), any());
        assertEquals("contact/list", viewName);
    }

    @Test
    void getContactWithValidIdShouldReturnCorrectViewNameAndAttributes() {
        int id = 1;
        when(contactService.findContactById(id)).thenReturn(Optional.of(new Contact()));

        String viewName = contactController.getContact(id, model);

        verify(model).addAttribute(eq("contact"), any());
        assertEquals("contact/details", viewName);
    }

    @Test
    void getContactWithInvalidIdShouldReturnNotFoundViewName() {
        int id = 1;
        when(contactService.findContactById(id)).thenReturn(Optional.empty());

        String viewName = contactController.getContact(id, model);

        assertEquals("notfound", viewName);
    }

    @Test
    void getContactFormShouldReturnCorrectViewNameAndAttributes() {
        String viewName = contactController.getContactForm(model);
        verify(model).addAttribute(eq("contact"), any());
        verify(model).addAttribute(eq("selectedContacts"), any());
        verify(model).addAttribute(eq("addresses"), any());
        verify(model).addAttribute(eq("contacts"), any());
        assertEquals("contact/create", viewName);
    }

    @Test
    void saveContactShouldRedirectToContactDetails() {
        Contact contact = new Contact();
        Contact fakeContact = new Contact();
        fakeContact.setId(1);
        when(contactService.saveContact(contact)).thenReturn(fakeContact);

        String redirectURL = contactController.saveContact(contact);

        assertEquals("redirect:/contact/1", redirectURL);
    }

    @Test
    void getUpdateContactWithValidIdShouldReturnCorrectViewNameAndAttributes() {
        int id = 1;
        when(contactService.findContactById(id)).thenReturn(Optional.of(new Contact()));

        String viewName = contactController.getUpdateContact(id, model);

        verify(model).addAttribute(eq("contact"), any());
        verify(model).addAttribute(eq("addresses"), any());
        verify(model).addAttribute(eq("contacts"), any());
        verify(model).addAttribute(eq("selectedContactIds"), any());
        assertEquals("contact/edit", viewName);
    }

    @Test
    void getUpdateContactWithInvalidIdShouldReturnNotFoundViewName() {
        int id = 1;
        when(contactService.findContactById(id)).thenReturn(Optional.empty());

        String viewName = contactController.getUpdateContact(id, model);

        assertEquals("notfound", viewName);
    }

    @Test
    void updateContactWithValidIdShouldRedirectToContactDetails() {
        int id = 1;
        Contact contact = new Contact();
        Contact fakeContact = new Contact();
        fakeContact.setId(id);
        when(contactService.updateContact(id, contact)).thenReturn(Optional.of(fakeContact));

        String redirectURL = contactController.updateContact(contact, id);

        assertEquals("redirect:/contact/1", redirectURL);
    }

    @Test
    void updateContactWithInvalidIdShouldRedirectToNotFound() {
        int id = 1;
        Contact contact = new Contact();
        when(contactService.updateContact(id, contact)).thenReturn(Optional.empty());

        String redirectURL = contactController.updateContact(contact, id);

        assertEquals("redirect:/notfound", redirectURL);
    }

    @Test
    void deleteContactByIdShouldRedirectToContactList() {
        int id = 1;

        String redirectURL = contactController.deleteContactById(id);

        assertEquals("redirect:/contact", redirectURL);
    }

    @Test
    void sortByFullNameShouldReturnCorrectViewNameAndAttributes() {
        String viewName = contactController.sortByFullName(model, "asc");

        verify(model).addAttribute(eq("contacts"), any());
        assertEquals("contact/list", viewName);
    }

    @Test
    void sortByLastUpdatedShouldReturnCorrectViewNameAndAttributes() {
        String viewName = contactController.sortByLastUpdated(model, "asc");

        verify(model).addAttribute(eq("contacts"), any());
        assertEquals("contact/list", viewName);
    }

    @Test
    void greetingsShouldReturnCorrectViewNameAndAttributes() {
        int id = 1;
        when(contactService.greetWithABirthDay(id)).thenReturn("Happy Birthday!");

        String viewName = contactController.greetings(id, model);

        verify(model).addAttribute(eq("greetingLine"), any());
        assertEquals("contact/greetingPage", viewName);
    }
}
