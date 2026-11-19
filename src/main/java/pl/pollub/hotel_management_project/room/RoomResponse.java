package pl.pollub.hotel_management_project.room;

import java.math.BigDecimal;

public record RoomResponse(
        Long id,
        String roomNumber,
        RoomType type,
        int capacity,
        BigDecimal pricePerNight,
        boolean available
) {
}