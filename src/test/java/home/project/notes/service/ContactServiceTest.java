package home.project.notes.service;

import home.project.notes.data.Contact;
import home.project.notes.exception.ResourceNotFoundException;
import home.project.notes.repos.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private AddressService addressService;

    private ContactService contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contactService = new ContactService(contactRepository, addressService);
    }

    @Test
    void getContactsShouldReturnContacts() {
        List<Contact> expectedContacts = Collections.singletonList(new Contact());
        when(contactRepository.findAll()).thenReturn(expectedContacts);

        List<Contact> actualContacts = contactService.getContacts();

        assertEquals(expectedContacts, actualContacts);
    }

    @Test
    void saveContactShouldSaveContact() {
        Contact contactToSave = new Contact();
        when(contactRepository.save(contactToSave)).thenReturn(contactToSave);

        Contact savedContact = contactService.saveContact(contactToSave);

        assertEquals(contactToSave, savedContact);
    }

    @Test
    void findContactByIdWithValidIdShouldReturnContact() {
        int id = 1;
        Contact expectedContact = new Contact();
        when(contactRepository.findById(id)).thenReturn(Optional.of(expectedContact));

        Contact actualContact = contactService.findContactById(id);

        assertEquals(expectedContact, actualContact);
    }

    @Test
    void findContactByIdWithInvalidIdShouldReturnThrow() {
        int nonExistingId = 999;
        when(contactRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactService.findContactById(nonExistingId));

        verify(contactRepository).findById(nonExistingId);
    }

    @Test
    void deleteContactWithValidIdShouldDeleteContact() {
        int id = 1;
        Contact contactToDelete = new Contact();
        when(contactRepository.findById(id)).thenReturn(Optional.of(contactToDelete));

        contactService.deleteContact(id);

        verify(contactRepository, times(1)).delete(contactToDelete);
    }

    @Test
    void updateContactWithValidIdShouldUpdateContact() {
        int id = 1;
        Contact existingContact = new Contact();
        Contact newContact = new Contact();
        when(contactRepository.findById(id)).thenReturn(Optional.of(existingContact));
        when(contactRepository.save(existingContact)).thenReturn(existingContact);

        Contact updatedContact = contactService.updateContact(id, newContact);

        assertEquals(existingContact, updatedContact);
    }

    @Test
    void updateContactWithInvalidIdShouldThrow() {
        int id = 1;
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactService.updateContact(id, new Contact()));

        verify(contactRepository, times(1)).findById(id);
    }

    @Test
    void greetWithABirthDayWithValidIdAndBirthDayShouldReturnBirthdayGreeting() {
        int id = 1;
        LocalDate today = LocalDate.now();
        Contact contactWithBirthDay = new Contact();
        contactWithBirthDay.setBirthDate(today);

        when(contactRepository.findById(id)).thenReturn(Optional.of(contactWithBirthDay));

        String greeting = contactService.greetWithABirthDay(id);

        assertEquals("Happy Birthday, " + contactWithBirthDay.getFullName() + "!", greeting);
    }

    @Test
    void greetWithABirthDayWithValidIdAndNotBirthDayShouldReturnNotBirthdayMessage() {
        int id = 1;
        LocalDate today = LocalDate.now();
        LocalDate birthDay = today.minusDays(1);
        Contact contactWithoutBirthDay = new Contact();
        contactWithoutBirthDay.setBirthDate(birthDay);

        when(contactRepository.findById(id)).thenReturn(Optional.of(contactWithoutBirthDay));

        String greeting = contactService.greetWithABirthDay(id);

        assertEquals("Sorry, birthday of " + contactWithoutBirthDay.getFullName() + " is " + birthDay + "!", greeting);
    }

    @Test
    void greetWithABirthDayWithInvalidIdShouldReturnErrorMessage() {
        int invalidId = 999;
        when(contactRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactService.greetWithABirthDay(invalidId));

        verify(contactRepository).findById(invalidId);
    }
}

