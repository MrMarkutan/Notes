package home.project.notes.controllers;

import home.project.notes.data.Address;
import home.project.notes.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private static final String NOT_IMPLEMENTED_YET = "notimplementedyet";
    private static final String VIEW_FOLDER = "address";
    private static final String REDIRECT = "redirect:/";

    private final AddressService addressService;

    @GetMapping
    public String getAddresses(Model model) {
        model.addAttribute("addresses", addressService.getAddresses());
        return VIEW_FOLDER + "/list";
    }

    @GetMapping("/add")
    public String getAddressForm(Model model) {
        return VIEW_FOLDER + "/create";
    }

    @PostMapping("/add")
    public String addAddress(@ModelAttribute Address address) {
        Address savedAddress = addressService.saveAddress(address);
        return REDIRECT + VIEW_FOLDER + "/" + savedAddress.getId();
    }

    @GetMapping("/{id}")
    public String getAddress(@PathVariable int id, Model model) {
        return addressService.getAddressById(id)
                .map(address -> {
                    model.addAttribute("address", address);
                    return VIEW_FOLDER + "/details";
                })
                .orElse(NOT_IMPLEMENTED_YET);
    }


    @GetMapping("/{id}/edit")
    public String getUpdateAddress(@PathVariable int id, Model model) {
        return addressService.getAddressById(id)
                .map(address -> {
                    model.addAttribute("address", address);
                    return VIEW_FOLDER + "/edit";
                })
                .orElse(NOT_IMPLEMENTED_YET);
    }

    @PostMapping("/{id}/edit")
    public String updateAddress(@PathVariable int id, @ModelAttribute Address address, Model model) {
        Optional<Address> optionalAddress = addressService.updateAddress(id, address);
        if (optionalAddress.isPresent()) {
            Address updatedAddress = optionalAddress.get();
            model.addAttribute("address", updatedAddress);
            return REDIRECT + VIEW_FOLDER + "/" + updatedAddress.getId();
        } else {
            return NOT_IMPLEMENTED_YET;
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteAddress(@PathVariable int id) {
        addressService.deleteAddress(id);
        return REDIRECT + VIEW_FOLDER + "/list";
    }
}
