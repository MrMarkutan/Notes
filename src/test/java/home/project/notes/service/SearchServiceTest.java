package home.project.notes.service;

import home.project.notes.data.Contact;
import home.project.notes.data.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock
    private ContactService contactService;

    @Mock
    private AddressService addressService;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchService = new SearchService(contactService, addressService);
    }

    @Test
    void searchByFullNameWithValidNamesShouldReturnSearchResult() {
        String firstName = "John";
        String lastName = "Doe";
        List<Contact> expectedContacts = Collections.singletonList(new Contact());

        when(contactService.getFirstNameContainsOrLastNameContains(firstName, lastName))
                .thenReturn(expectedContacts);

        SearchResult result = searchService.searchByFullName(firstName, lastName);

        assertEquals(expectedContacts, result.getContacts());
    }

    @Test
    void phoneSearchWithValidNumberShouldReturnSearchResult() {
        String phoneNumber = "123456789012";
        List<Contact> expectedContacts = Collections.singletonList(new Contact());

        when(contactService.getPhonesContaining(phoneNumber)).thenReturn(expectedContacts);

        SearchResult result = searchService.phoneSearch(phoneNumber);

        assertEquals(expectedContacts, result.getContacts());
    }

    @Test
    void yearSearchWithValidYearShouldReturnSearchResult() {
        int birthYear = 1990;
        List<Contact> expectedContacts = Collections.singletonList(new Contact());

        when(contactService.getBirthYearEquals(birthYear)).thenReturn(expectedContacts);

        SearchResult result = searchService.yearSearch(birthYear);

        assertEquals(expectedContacts, result.getContacts());
    }

    @Test
    void addressSearchWithValidAddressShouldReturnSearchResult() {
        String country = "USA";
        String city = "New York";
        String street = "Broadway";
        Integer building = 123;
        Integer apartment = 456;

        List<Contact> expectedContacts = Collections.singletonList(new Contact());

        when(contactService.searchByAddress(country, city, street, building, apartment))
                .thenReturn(expectedContacts);

        SearchResult result = searchService.addressSearch(country, city, street, building, apartment);

        assertEquals(expectedContacts, result.getContacts());
    }

}
