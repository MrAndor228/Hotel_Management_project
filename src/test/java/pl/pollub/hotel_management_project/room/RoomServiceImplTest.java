package pl.pollub.hotel_management_project.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pollub.hotel_management_project.common.BusinessRuleViolationException;
import pl.pollub.hotel_management_project.common.DuplicateResourceException;
import pl.pollub.hotel_management_project.common.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        roomService = new RoomServiceImpl(roomRepository, new RoomMapper());
    }

    @Test
    void createRoom_shouldSaveRoom_whenDataIsValid() {
        RoomRequest request = new RoomRequest("101", RoomType.DOUBLE, 2, new BigDecimal("250.00"));
        when(roomRepository.existsByRoomNumber("101")).thenReturn(false);
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RoomResponse response = roomService.createRoom(request);

        assertEquals("101", response.roomNumber());
        assertEquals(RoomType.DOUBLE, response.type());
        assertTrue(response.available());
        verify(roomRepository).save(any(Room.class));
    }

    @Test
    void createRoom_shouldThrow_whenRoomNumberAlreadyExists() {
        RoomRequest request = new RoomRequest("101", RoomType.DOUBLE, 2, new BigDecimal("250.00"));
        when(roomRepository.existsByRoomNumber("101")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> roomService.createRoom(request));
        verify(roomRepository, never()).save(any());
    }

    @Test
    void createRoom_shouldThrow_whenCapacityExceedsRoomTypeLimit() {
        RoomRequest request = new RoomRequest("102", RoomType.SINGLE, 3, new BigDecimal("150.00"));
        when(roomRepository.existsByRoomNumber("102")).thenReturn(false);

        assertThrows(BusinessRuleViolationException.class, () -> roomService.createRoom(request));
        verify(roomRepository, never()).save(any());
    }
}