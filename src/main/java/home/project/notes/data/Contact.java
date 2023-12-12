package home.project.notes.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "contact")
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @ManyToOne
    private Address homeAddress;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "last_update")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;

    @ElementCollection
    @Column(name = "phone")
    @CollectionTable(name = "contact_phones", joinColumns = @JoinColumn(name = "contact_id"))
    private Set<String> phones = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "contact_id")
    private Set<Contact> partners = new LinkedHashSet<>();

    @ElementCollection
    @Column(name = "note")
    @CollectionTable(name = "contact_notes", joinColumns = @JoinColumn(name = "contact_id"))
    private List<String> notes = new ArrayList<>();

    @Transient
    private List<String> selectedContactIds = new ArrayList<>();

    public Contact update(Contact from) {
        if (from.getFirstName() != null) this.setFirstName(from.getFirstName());
        if (from.getLastName() != null) this.setLastName(from.getLastName());
        if (from.getHomeAddress() != null) this.setHomeAddress(from.getHomeAddress());
        if (from.getPhoneNumber() != null && !from.getPhoneNumber().isEmpty())
            this.setPhoneNumber(from.getPhoneNumber());
        if (from.getBirthDate() != null) this.setBirthDate(from.getBirthDate());
        if (from.getPhones() != null) this.setPhones(from.getPhones());
        if (from.getPartners() != null) this.setPartners(from.getPartners());
        if (from.getNotes() != null) this.setNotes(from.getNotes());

        lastUpdate = LocalDateTime.now();
        return this;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String listPhones() {
        return String.join("\n", phones);
    }

    public String printNotes() {
        return String.join("\n", notes);
    }
}