package home.project.notes.service;

import home.project.notes.data.Contact;
import home.project.notes.data.SearchResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {
    private final ContactService contactService;
    private final AddressService addressService;

    public SearchResult searchByFullName(String firstName, String lastName) {
        SearchResult result = new SearchResult();

        List<Contact> contacts = contactService.getFirstNameContainsOrLastNameContains(firstName, lastName).stream()
                .distinct()
                .toList();

        result.setContacts(contacts);

        return result;
    }

    public SearchResult phoneSearch(String number) {
        SearchResult result = new SearchResult();
        List<Contact> contacts = contactService.getPhonesContaining(number);
        result.setContacts(contacts);
        return result;
    }

    public SearchResult yearSearch(int year) {
        SearchResult result = new SearchResult();
        List<Contact> contacts = contactService.getBirthYearEquals(year);
        result.setContacts(contacts);
        return result;
    }
}
