package home.project.notes.service;

import home.project.notes.data.Address;
import home.project.notes.data.Contact;
import home.project.notes.data.PhoneNumber;
import home.project.notes.repos.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

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
                .map(existingContact -> existingContact.update(newContact))
                .map(this::saveContact);
    }

    private void checkContact(Contact contact) {
        checkPhoneNumbers(contact);
        checkHomeAddress(contact);
    }

    private void checkHomeAddress(Contact contact) {
        Address homeAddress = contact.getHomeAddress();
        if (homeAddress != null) {
            Address savedAddress = addressService.saveAddress(homeAddress);
            contact.setHomeAddress(savedAddress);
        }
    }

    private void checkPhoneNumbers(Contact contact) {
        Set<PhoneNumber> savedNumbers = new LinkedHashSet<>();
        for (PhoneNumber phoneNumber : contact.getPhoneNumbers()) {
            if (phoneNumber != null) {
                savedNumbers.add(phoneNumberService.savePhoneNumber(phoneNumber));
            }
        }
        contact.setPhoneNumbers(savedNumbers);
    }
}
