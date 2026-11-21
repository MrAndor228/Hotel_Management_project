package pl.pollub.hotel_management_project.guest;

import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public Guest toEntity(GuestRequest request) {
        return new Guest(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phoneNumber(),
                request.documentNumber()
        );
    }

    public void updateEntity(Guest guest, GuestRequest request) {
        guest.setFirstName(request.firstName());
        guest.setLastName(request.lastName());
        guest.setEmail(request.email());
        guest.setPhoneNumber(request.phoneNumber());
        guest.setDocumentNumber(request.documentNumber());
    }

    public GuestResponse toResponse(Guest guest) {
        return new GuestResponse(
                guest.getId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getEmail(),
                guest.getPhoneNumber(),
                guest.getDocumentNumber(),
                guest.getCreatedAt()
        );
    }
}
