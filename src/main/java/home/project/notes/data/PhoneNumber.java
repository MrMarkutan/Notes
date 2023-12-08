package home.project.notes.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "phone_number", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"number"})
})
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number", unique = true, length = 20)
    @Pattern(regexp = "^\\+([0-9]){12}$", message = "Valid phone number starts with + and has 12 digits (e.q. +380501234567)")
    private String number;

    public PhoneNumber(String number) {
        this.number = number;
    }

    public PhoneNumber update(PhoneNumber from) {
        if (from.getNumber() != null) this.setNumber(from.getNumber());

        return this;
    }

    @Override
    public String toString() {
        return number;
    }
}