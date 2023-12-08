package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.data.Contact;
import home.project.notes.repos.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    }

    private void checkHomeAddress(Contact contact) {
        Address homeAddress = contact.getHomeAddress();
        if (homeAddress != null) {
            Address savedAddress = addressService.saveAddress(homeAddress);
            contact.setHomeAddress(savedAddress);
        }
    }
}
