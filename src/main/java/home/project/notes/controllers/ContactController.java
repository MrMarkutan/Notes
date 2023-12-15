package home.project.notes.controllers;

import home.project.notes.data.Contact;
import home.project.notes.exception.ResourceNotFoundException;
import home.project.notes.service.AddressService;
import home.project.notes.service.ContactService;
import home.project.notes.utils.Util;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactController {
    private static final String CONTACT_VIEW_FOLDER = "contact";
    private static final String REDIRECT = "redirect:/";

    private final ContactService contactService;
    private final AddressService addressService;

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "notfound";
    }

    @GetMapping({"/", "", "/index"})
    public String getAllContacts(Model model) {
        model.addAttribute("contacts", contactService.getContacts());
        return CONTACT_VIEW_FOLDER + "/list";
    }

    @GetMapping("/{id}")
    public String getContact(@PathVariable int id, Model model) {
        model.addAttribute("contact", contactService.findContactById(id));
        return CONTACT_VIEW_FOLDER + "/details";
    }

    @GetMapping("/add")
    public String getContactForm(Model model) {
        model.addAttribute("contact", Util.buildContact());
        model.addAttribute("selectedContacts", new ArrayList<Integer>());
        model.addAttribute("addresses", addressService.getAddresses());
        model.addAttribute("contacts", contactService.getContacts());
        return CONTACT_VIEW_FOLDER + "/create";
    }

    @PostMapping("/add")
    public String saveContact(@ModelAttribute Contact contact) {
        Contact savedContact = contactService.saveContact(contact);
        return REDIRECT + CONTACT_VIEW_FOLDER + "/" + savedContact.getId();
    }


    @GetMapping("{id}/edit")
    public String getUpdateContact(@PathVariable int id, Model model) {
        Contact contact = contactService.findContactById(id);
        model.addAttribute("contact", contact);
        model.addAttribute("addresses", addressService.getAddresses());
        model.addAttribute("contacts", contactService.getContacts());
        model.addAttribute("selectedContactIds",
                contact.getPartners().stream().map(Contact::getId).toList());
        return CONTACT_VIEW_FOLDER + "/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateContact(@ModelAttribute Contact contact, @PathVariable int id) {
        Contact updated = contactService.updateContact(id, contact);
        return REDIRECT + CONTACT_VIEW_FOLDER + "/" + updated.getId();
    }

    @GetMapping("/{id}/delete")
    public String deleteContactById(@PathVariable int id) {
        contactService.deleteContact(id);
        return REDIRECT + CONTACT_VIEW_FOLDER;
    }

    @GetMapping("/sortByFullName")
    public String sortByFullName(Model model, @RequestParam(defaultValue = "asc") String direction) {

        model.addAttribute("contacts", contactService.findAllSortedByFullName(direction));
        return CONTACT_VIEW_FOLDER + "/list";
    }

    @GetMapping("/sortByLastUpdate")
    public String sortByLastUpdated(Model model, @RequestParam(defaultValue = "asc") String direction) {
        model.addAttribute("contacts", contactService.findAllSortedByLastUpdated(direction));
        return CONTACT_VIEW_FOLDER + "/list";
    }

    @GetMapping("/{id}/greetWithTheBirthDay")
    public String greetings(@PathVariable int id, Model model) {
        model.addAttribute("greetingLine", contactService.greetWithABirthDay(id));
        return CONTACT_VIEW_FOLDER + "/greetingPage";
    }
}
