package home.project.notes.repos;

import home.project.notes.data.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {
    PhoneNumber findByNumber(String number);
    boolean existsByNumber(String number);
}