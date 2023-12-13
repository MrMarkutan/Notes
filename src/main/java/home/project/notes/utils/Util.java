package home.project.notes.utils;

import home.project.notes.data.Contact;
import org.springframework.data.domain.Sort;

public class Util {
    public static Contact buildContact() {
        return Contact.builder().build();
    }

    public static Sort getSort(String direction, String... properties) {
        Sort sort = Sort.by(properties);
        if ("desc".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        }
        return sort;
    }
}
