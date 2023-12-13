package home.project.notes.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private List<Contact> contacts;
    private List<Address> addresses;
}
