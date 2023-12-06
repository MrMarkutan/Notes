package home.project.notes.controllers;

import home.project.notes.data.Address;
import home.project.notes.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/list")
    public List<Address> getAddresses() {
        return addressService.getAddresses();
    }

    @PostMapping("/add")
    public Address addAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }
}
