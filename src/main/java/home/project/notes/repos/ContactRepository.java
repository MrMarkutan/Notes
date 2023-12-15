package home.project.notes.repos;

import home.project.notes.data.Address;
import home.project.notes.data.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query("SELECT c FROM Contact c WHERE (:firstName IS NULL OR c.firstName = :firstName) " +
            "AND (:lastName IS NULL OR c.lastName = :lastName)")
    List<Contact> findByFirstNameOrLastName(@Param("firstName") String firstName,
                                            @Param("lastName") String lastName);


    List<Contact> findByPhonesContaining(String number);

    @Query("SELECT c FROM Contact c WHERE YEAR(c.birthDate) = :year")
    List<Contact> findByBirthYear(@Param("year") int year);

    List<Contact> findByHomeAddress(Address homeAddress);
}