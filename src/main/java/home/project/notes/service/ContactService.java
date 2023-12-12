package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.data.Contact;
import home.project.notes.repos.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void removeNoteFromContact(int id, int nId) {
        findContactById(id)
                .ifPresent(contact -> {
                    contact.setNotes(removeNoteAtIndex(contact.getNotes(), nId));
                    updateContact(id, contact);
                });
    }

    private List<String> removeNoteAtIndex(List<String> notes, int index) {
        return IntStream.range(0, notes.size())
                .filter(i -> i != index)
                .mapToObj(notes::get)
                .collect(Collectors.toList());
    }
}