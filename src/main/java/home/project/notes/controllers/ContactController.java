package home.project.notes.controllers;

import home.project.notes.data.Contact;
import home.project.notes.service.AddressService;
import home.project.notes.service.ContactService;
import home.project.notes.service.PhoneNumberService;
import home.project.notes.utils.Builders;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static home.project.notes.utils.Builders.buildPhoneNumber;

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
    private final PhoneNumberService numberService;

    @GetMapping
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
        model.addAttribute("addresses", addressService.getAddresses());
        return CONTACT_VIEW_FOLDER + "/create";
    }

    @PostMapping("/add")
    public String saveContact(@ModelAttribute Contact contact,
                              @RequestParam String phoneNumber) {
        contact.getPhoneNumbers().add(buildPhoneNumber(phoneNumber));

        Contact savedContact = contactService.saveContact(contact);
        return REDIRECT + CONTACT_VIEW_FOLDER + "/" + savedContact.getId();
    }

    @GetMapping("{id}/edit")
    public String getUpdateContact(@PathVariable int id, Model model) {
        return contactService.findContactById(id)
                .map(contact -> {
                    model.addAttribute("contact", contact);
                    return CONTACT_VIEW_FOLDER + "/edit";
                })
                .orElse(NOT_IMPLEMENTED_YET);
    }

    @PostMapping("/{id}/edit")
    public String updateContact(@ModelAttribute Contact contact, @PathVariable int id, Model model) {
        return contactService.updateContact(id, contact)
                .map(updatedContact -> {
                    model.addAttribute("contact", updatedContact);
                    return REDIRECT + CONTACT_VIEW_FOLDER + "/" + updatedContact.getId();
                })
                .orElse(REDIRECT + NOT_IMPLEMENTED_YET);
    }

    @GetMapping("/{id}/delete")
    public String deleteContactById(@PathVariable int id) {
        contactService.deleteContact(id);
        return REDIRECT + CONTACT_VIEW_FOLDER + "/list";
    }

    @GetMapping("/{id}/deleteNumber/{nId}")
    public String deleteNumber(@PathVariable int id,
                               @PathVariable int nId,
                               Model model) {
        contactService.deleteNumberFromContact(id, nId);
        return REDIRECT + CONTACT_VIEW_FOLDER + "/" + id + "/edit";
    }

    @GetMapping("/{id}/addNumber")
    public String getAddNumber(@PathVariable int id,
                               Model model) {
        return contactService.findContactById(id)
                .map(contact -> {
                    model.addAttribute("contact", contact);
                    return NUMBER_VIEW_FOLDER + "/create";
                }).orElse(NOT_IMPLEMENTED_YET);
    }

    @PostMapping("/{id}/addNumber")
    public String addNumberToContact(@RequestParam String phoneNumber,
                                     @PathVariable int id,
                                     Model model) {
        return contactService.findContactById(id)
                .map(contact -> {
                    contact.getPhoneNumbers().add(buildPhoneNumber(phoneNumber));
                    contactService.updateContact(id, contact);
                    model.addAttribute("contact", contact);
                    return REDIRECT + CONTACT_VIEW_FOLDER + "/" + id + "/edit";
                }).orElse(NOT_IMPLEMENTED_YET);
    }

    @GetMapping("/{id}/updateNumber/{nId}")
    public String getUpdateNumber(@PathVariable int id,
                                  @PathVariable int nId,
                                  Model model) {
        numberService.getPhoneNumberById(nId)
                .ifPresent(phoneNumber -> {
                            model.addAttribute("contact_id", id);
                            model.addAttribute("phoneNumber", phoneNumber);
                        }
                );

        return NUMBER_VIEW_FOLDER + "/edit";
    }

    @PostMapping("/{id}/updateNumber/{nId}")
    public String updateNumber(@PathVariable int id,
                               @PathVariable int nId,
                               @RequestParam String phoneNumber) {

        numberService.updatePhoneNumber(nId, Builders.buildPhoneNumber(phoneNumber));
        return REDIRECT + CONTACT_VIEW_FOLDER + "/" + id + "/edit";

    }
}
