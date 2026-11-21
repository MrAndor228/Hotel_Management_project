package pl.pollub.hotel_management_project.guest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<GuestResponse> getGuests(
            @RequestParam(name = "lastName", required = false) String lastName) {
        return guestService.getGuests(lastName);
    }

    @GetMapping("/{id}")
    public GuestResponse getGuestById(@PathVariable("id") Long id) {
        return guestService.getGuestById(id);
    }

    @PostMapping
    public ResponseEntity<GuestResponse> createGuest(@Valid @RequestBody GuestRequest request) {
        GuestResponse createdGuest = guestService.createGuest(request);
        return ResponseEntity
                .created(URI.create("/api/guests/" + createdGuest.id()))
                .body(createdGuest);
    }
}
