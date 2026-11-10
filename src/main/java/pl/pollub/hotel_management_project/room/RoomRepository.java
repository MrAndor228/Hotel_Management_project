package pl.pollub.hotel_management_project.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByRoomNumber(String roomNumber);

    List<Room> findByType(RoomType type);

    List<Room> findByAvailable(boolean available);

    List<Room> findByTypeAndAvailable(RoomType type, boolean available);
}