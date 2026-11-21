package pl.pollub.hotel_management_project.guest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GuestRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must have at most 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must have at most 50 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\+?[0-9 ]{9,15}$", message = "Phone number must contain 9-15 digits")
        String phoneNumber,

        @NotBlank(message = "Document number is required")
        @Size(max = 20, message = "Document number must have at most 20 characters")
        String documentNumber
) {
}
