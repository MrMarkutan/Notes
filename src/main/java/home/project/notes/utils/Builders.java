package home.project.notes.utils;

import home.project.notes.data.Contact;

public class Builders {
    public static Contact buildContact() {
        return Contact.builder().build();
    }
}
