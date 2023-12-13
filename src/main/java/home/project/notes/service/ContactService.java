package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.data.Contact;
import home.project.notes.repos.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    public Optional<Contact> findContactById(Integer id) {
        return contactRepository.findById(id);
    }

    public void deleteContact(int id) {
        findContactById(id).ifPresent(contactRepository::delete);
    }

    public Optional<Contact> updateContact(int id, Contact newContact) {
        return findContactById(id)
                .map(existingContact -> {
                    existingContact.update(newContact);
                    return existingContact;
                })
                .map(this::saveContact);
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
        return findContactById(id)
                .map(contact -> {
                    LocalDate today = LocalDate.now();
                    return isBirthDay(contact, today)
                            ? "Happy Birthday, " + contact.getFullName() + "!"
                            : "Sorry, birthday of " + contact.getFullName() + " is " + contact.getBirthDate() + "!";
                })
                .orElse("Contact not found with ID: " + id);
    }

    private boolean isBirthDay(Contact contact, LocalDate date) {
        return contact.getBirthDate().getDayOfMonth() == date.getDayOfMonth()
                && contact.getBirthDate().getMonthValue() == date.getMonthValue();
    }
}