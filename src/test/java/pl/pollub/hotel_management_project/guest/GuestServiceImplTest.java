package pl.pollub.hotel_management_project.guest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pollub.hotel_management_project.common.DuplicateResourceException;
import pl.pollub.hotel_management_project.common.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {

    @Mock
    private GuestRepository guestRepository;

    private GuestServiceImpl guestService;

    @BeforeEach
    void setUp() {
        guestService = new GuestServiceImpl(guestRepository, new GuestMapper());
    }

    private GuestRequest validRequest() {
        return new GuestRequest("Jan", "Kowalski", "jan@example.com", "+48 123 456 789", "ABC123456");
    }

    @Test
    void createGuest_shouldSaveGuest_whenDataIsValid() {
        when(guestRepository.existsByEmail("jan@example.com")).thenReturn(false);
        when(guestRepository.existsByDocumentNumber("ABC123456")).thenReturn(false);
        when(guestRepository.save(any(Guest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GuestResponse response = guestService.createGuest(validRequest());

        assertEquals("Jan", response.firstName());
        assertEquals("jan@example.com", response.email());
        verify(guestRepository).save(any(Guest.class));
    }

    @Test
    void createGuest_shouldThrow_whenEmailAlreadyExists() {
        when(guestRepository.existsByEmail("jan@example.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> guestService.createGuest(validRequest()));
        verify(guestRepository, never()).save(any());
    }

    @Test
    void createGuest_shouldThrow_whenDocumentNumberAlreadyExists() {
        when(guestRepository.existsByEmail("jan@example.com")).thenReturn(false);
        when(guestRepository.existsByDocumentNumber("ABC123456")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> guestService.createGuest(validRequest()));
        verify(guestRepository, never()).save(any());
    }
}
