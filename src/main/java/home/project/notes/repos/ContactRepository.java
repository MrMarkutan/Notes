package home.project.notes.repos;

import home.project.notes.data.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("select c from Contact c order by concat(c.firstName, c.lastName)")
    List<Contact> findContactsBySortedByFullName();

}