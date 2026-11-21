package pl.pollub.hotel_management_project.room;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<RoomResponse> getRooms(
            @RequestParam(name = "type", required = false) RoomType type,
            @RequestParam(name = "available", required = false) Boolean available) {
        return roomService.getRooms(type, available);
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable("id") Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        RoomResponse createdRoom = roomService.createRoom(request);
        return ResponseEntity
                .created(URI.create("/api/rooms/" + createdRoom.id()))
                .body(createdRoom);
    }

    @PutMapping("/{id}")
    public RoomResponse updateRoom(@PathVariable("id") Long id,
                                   @Valid @RequestBody RoomRequest request) {
        return roomService.updateRoom(id, request);
    }

    @PatchMapping("/{id}/availability")
    public RoomResponse changeAvailability(@PathVariable("id") Long id,
                                           @RequestParam("available") boolean available) {
        return roomService.changeAvailability(id, available);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable("id") Long id) {
        roomService.deleteRoom(id);
    }
}
