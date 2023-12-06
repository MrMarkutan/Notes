package home.project.notes.service;

import home.project.notes.data.PhoneNumber;
import home.project.notes.repos.PhoneNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhoneNumberService {
    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumber savePhoneNumber(PhoneNumber phoneNumber) {
        PhoneNumber toSave;
        String number = phoneNumber.getNumber();
        if (phoneNumberRepository.existsByNumber(number)) {
            toSave = phoneNumberRepository.findByNumber(number);
        } else {
            toSave = phoneNumberRepository.save(phoneNumber);
        }
        return toSave;
    }

    public List<PhoneNumber> getNumbers() {
        return phoneNumberRepository.findAll();
    }

    public Optional<PhoneNumber> getPhoneNumberById(int id) {
        return phoneNumberRepository.findById(id);
    }

    public void deletePhoneNumber(int id) {
        getPhoneNumberById(id).ifPresent(phoneNumberRepository::delete);
    }

    public Optional<PhoneNumber> updatePhoneNumber(int id, PhoneNumber phoneNumber) {
        return getPhoneNumberById(id)
                .map(existingNumber -> existingNumber.update(phoneNumber))
                .map(this::savePhoneNumber);
    }
}
