package pl.pollub.hotel_management_project.room;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.hotel_management_project.common.BusinessRuleViolationException;
import pl.pollub.hotel_management_project.common.DuplicateResourceException;
import pl.pollub.hotel_management_project.common.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private static final String RESOURCE_NAME = "Room";

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        if (roomRepository.existsByRoomNumber(request.roomNumber())) {
            throw new DuplicateResourceException("Room with number " + request.roomNumber() + " already exists");
        }
        validateCapacity(request);
        Room savedRoom = roomRepository.save(roomMapper.toEntity(request));
        return roomMapper.toResponse(savedRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomById(Long id) {
        return roomMapper.toResponse(findRoomOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getRooms(RoomType type, Boolean available) {
        List<Room> rooms;
        if (type != null && available != null) {
            rooms = roomRepository.findByTypeAndAvailable(type, available);
        } else if (type != null) {
            rooms = roomRepository.findByType(type);
        } else if (available != null) {
            rooms = roomRepository.findByAvailable(available);
        } else {
            rooms = roomRepository.findAll();
        }
        return rooms.stream()
                .map(roomMapper::toResponse)
                .toList();
    }

    @Override
    public RoomResponse updateRoom(Long id, RoomRequest request) {
        Room room = findRoomOrThrow(id);
        if (roomRepository.existsByRoomNumberAndIdNot(request.roomNumber(), id)) {
            throw new DuplicateResourceException("Room with number " + request.roomNumber() + " already exists");
        }
        validateCapacity(request);
        roomMapper.updateEntity(room, request);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse changeAvailability(Long id, boolean available) {
        Room room = findRoomOrThrow(id);
        room.setAvailable(available);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = findRoomOrThrow(id);
        roomRepository.delete(room);
    }

    private Room findRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id));
    }

    private void validateCapacity(RoomRequest request) {
        if (!request.type().allowsCapacity(request.capacity())) {
            throw new BusinessRuleViolationException(
                    "Room type " + request.type() + " allows at most "
                            + request.type().getMaxCapacity() + " guests");
        }
    }
}