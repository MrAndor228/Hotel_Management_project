package pl.pollub.hotel_management_project.guest;

import java.time.LocalDateTime;

public record GuestResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String documentNumber,
        LocalDateTime createdAt
) {
}
