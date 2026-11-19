package pl.pollub.hotel_management_project.room;

import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public Room toEntity(RoomRequest request) {
        return new Room(
                request.roomNumber(),
                request.type(),
                request.capacity(),
                request.pricePerNight()
        );
    }

    public void updateEntity(Room room, RoomRequest request) {
        room.setRoomNumber(request.roomNumber());
        room.setType(request.type());
        room.setCapacity(request.capacity());
        room.setPricePerNight(request.pricePerNight());
    }

    public RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomNumber(),
                room.getType(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.isAvailable()
        );
    }
}