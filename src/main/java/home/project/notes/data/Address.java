package home.project.notes.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "address", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"country", "city", "street", "building", "apartment"})
})
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "building")
    private Integer building;

    @Column(name = "apartment")
    private Integer apartment;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "homeAddress")
    @JsonIgnore
    private Set<Contact> contacts = new LinkedHashSet<>();

    public Address update(Address from) {
        if (from.getCountry() != null) this.setCountry(from.getCountry());
        if (from.getCity() != null) this.setCity(from.getCity());
        if (from.getStreet() != null) this.setStreet(from.getStreet());
        if (from.getBuilding() != null) this.setBuilding(from.getBuilding());
        if (from.getApartment() != null) this.setApartment(from.getApartment());

        return this;
    }

    @Override
    public String toString() {
        return country + ", " + city + ", " + street + ", "
                + building + ", apt. " + apartment;
    }
}