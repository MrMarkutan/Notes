package home.project.notes.utils;

import home.project.notes.data.PhoneNumber;

public class Builders {
    public static PhoneNumber buildPhoneNumber(String phoneNumber) {
        return PhoneNumber.builder()
                .number(phoneNumber)
                .build();
    }
}
