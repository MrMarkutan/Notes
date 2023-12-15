package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.data.Contact;
import home.project.notes.exception.ResourceNotFoundException;
import home.project.notes.repos.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static home.project.notes.utils.Util.getSort;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final AddressService addressService;

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    public Contact saveContact(Contact contact) {
        checkContact(contact);
        return contactRepository.save(contact);
    }

    public Contact findContactById(Integer id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Contact with ID: %d was not found.", id)
                ));
    }

    public void deleteContact(int id) {
        contactRepository.delete(findContactById(id));
    }

    public Contact updateContact(int id, Contact newContact) {
        Contact updated = findContactById(id).update(newContact);
        return saveContact(updated);
    }

    private void checkContact(Contact contact) {
        checkHomeAddress(contact);
        if (!contact.getPhones().isEmpty() && !phonesMatchRegex(contact.getPhones())) {
            throw new NumberFormatException("Wrong phone number!");
        }
    }

    private boolean phonesMatchRegex(Set<String> phones) {
        return phones.stream().allMatch(phone -> phone.matches("\\d{12}"));
    }

    private void checkHomeAddress(Contact contact) {
        Address homeAddress = contact.getHomeAddress();
        if (homeAddress != null) {
            Address savedAddress = addressService.saveAddress(homeAddress);
            contact.setHomeAddress(savedAddress);
        }
    }


    public List<Contact> findAllSortedByFullName(String direction) {
        Sort sort = getSort(direction, "firstName", "lastName");
        return contactRepository.findAll(sort);
    }


    public List<Contact> findAllSortedByLastUpdated(String direction) {
        Sort sort = getSort(direction, "lastUpdate");
        return contactRepository.findAll(sort);
    }

    public String greetWithABirthDay(int id) {
        Contact contactById = findContactById(id);
        return isBirthDay(contactById, LocalDate.now())
                ? "Happy Birthday, " + contactById.getFullName() + "!"
                : "Sorry, birthday of " + contactById.getFullName() + " is " + contactById.getBirthDate() + "!";
    }

    private boolean isBirthDay(Contact contact, LocalDate date) {
        return contact.getBirthDate().getDayOfMonth() == date.getDayOfMonth()
                && contact.getBirthDate().getMonthValue() == date.getMonthValue();
    }

    public List<Contact> getFirstNameContainsOrLastNameContains(String firstName, String lastName) {
        return contactRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    public List<Contact> getPhonesContaining(String number) {
        return contactRepository.findByPhonesContaining(number);
    }

    public List<Contact> getBirthYearEquals(int year) {
        return contactRepository.findByBirthYear(year);
    }

    public List<Contact> searchByAddress(String country, String city, String street, Integer building, Integer apartment) {
        List<Address> addresses = addressService.searchAddresses(country, city, street, building, apartment);
        return addresses.stream()
                .flatMap(address -> contactRepository.findByHomeAddress(address).stream())
                .toList();
    }
}