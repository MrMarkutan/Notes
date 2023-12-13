package home.project.notes.controllers;

import home.project.notes.data.Contact;
import home.project.notes.service.AddressService;
import home.project.notes.service.ContactService;
import home.project.notes.utils.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactController {
    private static final String CONTACT_VIEW_FOLDER = "contact";
    private static final String NUMBER_VIEW_FOLDER = "numbers";
    private static final String REDIRECT = "redirect:/";

    private static final String NOT_IMPLEMENTED_YET = "notimplementedyet";
    private final ContactService contactService;
    private final AddressService addressService;

    @GetMapping({"/", "", "/index"})
    public String getAllContacts(Model model) {
        model.addAttribute("contacts", contactService.getContacts());
        return CONTACT_VIEW_FOLDER + "/list";
    }

    @GetMapping("/{id}")
    public String getContact(@PathVariable int id, Model model) {
        return contactService.findContactById(id)
                .map(contact -> {
                    model.addAttribute("contact", contact);
                    return CONTACT_VIEW_FOLDER + "/details";
                })
                .orElse(NOT_IMPLEMENTED_YET);
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
        return contactService.findContactById(id)
                .map(contact -> {
                    model.addAttribute("contact", contact);
                    model.addAttribute("addresses", addressService.getAddresses());
                    model.addAttribute("contacts", contactService.getContacts());
                    model.addAttribute("selectedContactIds",
                            contact.getPartners().stream().map(Contact::getId).toList());
                    return CONTACT_VIEW_FOLDER + "/edit";
                })
                .orElse(NOT_IMPLEMENTED_YET);
    }

    @PostMapping("/{id}/edit")
    public String updateContact(@ModelAttribute Contact contact, @PathVariable int id) {

        return contactService.updateContact(id, contact)
                .map(updatedContact -> REDIRECT + CONTACT_VIEW_FOLDER + "/" + updatedContact.getId())
                .orElse(REDIRECT + NOT_IMPLEMENTED_YET);
    }

    @GetMapping("/{id}/delete")
    public String deleteContactById(@PathVariable int id) {
        contactService.deleteContact(id);
        return REDIRECT + CONTACT_VIEW_FOLDER;
    }

    //    @GetMapping("/sortByFullName")
//    public String sortByFullName(Model model) {
//
//        model.addAttribute("contacts", contactService.findAllSortedByFullName());
//        return CONTACT_VIEW_FOLDER + "/list";
//    }
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
        model.addAttribute("greetingLine",contactService.greetWithABirthDay(id));
        return CONTACT_VIEW_FOLDER + "/greetingPage";
    }
}
