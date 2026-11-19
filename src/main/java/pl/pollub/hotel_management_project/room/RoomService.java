package pl.pollub.hotel_management_project.room;

import java.util.List;

public interface RoomService {

    RoomResponse createRoom(RoomRequest request);

    RoomResponse getRoomById(Long id);

    List<RoomResponse> getRooms(RoomType type, Boolean available);

    RoomResponse updateRoom(Long id, RoomRequest request);

    RoomResponse changeAvailability(Long id, boolean available);

    void deleteRoom(Long id);
}