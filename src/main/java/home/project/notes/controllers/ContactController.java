package home.project.notes.controllers;

import home.project.notes.data.Contact;
import home.project.notes.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/list")
    public List<Contact> getAllContacts() {
        return contactService.getContacts();
    }

    @PostMapping("/add")
    public Contact saveContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    @PutMapping("/update/{id}")
    public Contact updateContact(@RequestBody Contact contact, @PathVariable int id) {

        return contactService.updateContact(id, contact).get();
    }
}
