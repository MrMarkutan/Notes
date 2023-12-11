package home.project.notes.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import home.project.notes.utils.Builders;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
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

    public Contact update(Contact from) {
        if (from.getFirstName() != null) this.setFirstName(from.getFirstName());
        if (from.getLastName() != null) this.setLastName(from.getLastName());
        if (from.getHomeAddress() != null) this.setHomeAddress(from.getHomeAddress());
        if (from.getPhoneNumber() != null && !from.getPhoneNumber().isEmpty())
            this.setPhoneNumber(from.getPhoneNumber());
        if (from.getBirthDate() != null) this.setBirthDate(from.getBirthDate());
        if (from.getPhones() != null) this.setPhones(from.getPhones());
        lastUpdate = LocalDateTime.now();
        return this;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String listPhones() {
        return String.join("\n", phones);
    }
}