package pl.pollub.hotel_management_project.guest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    boolean existsByEmail(String email);

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByDocumentNumberAndIdNot(String documentNumber, Long id);

    List<Guest> findByLastNameContainingIgnoreCase(String lastName);
}
