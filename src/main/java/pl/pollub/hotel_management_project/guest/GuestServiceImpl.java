package pl.pollub.hotel_management_project.guest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.hotel_management_project.common.DuplicateResourceException;
import pl.pollub.hotel_management_project.common.ResourceNotFoundException;

import java.util.List;

@Service
@Transactional
public class GuestServiceImpl implements GuestService {

    private static final String RESOURCE_NAME = "Guest";

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestServiceImpl(GuestRepository guestRepository, GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    @Override
    public GuestResponse createGuest(GuestRequest request) {
        if (guestRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Guest with email " + request.email() + " already exists");
        }
        if (guestRepository.existsByDocumentNumber(request.documentNumber())) {
            throw new DuplicateResourceException("Guest with this document number already exists");
        }
        Guest savedGuest = guestRepository.save(guestMapper.toEntity(request));
        return guestMapper.toResponse(savedGuest);
    }

    @Override
    @Transactional(readOnly = true)
    public GuestResponse getGuestById(Long id) {
        return guestMapper.toResponse(findGuestOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestResponse> getGuests(String lastName) {
        List<Guest> guests = (lastName == null || lastName.isBlank())
                ? guestRepository.findAll()
                : guestRepository.findByLastNameContainingIgnoreCase(lastName);
        return guests.stream()
                .map(guestMapper::toResponse)
                .toList();
    }

    @Override
    public GuestResponse updateGuest(Long id, GuestRequest request) {
        Guest guest = findGuestOrThrow(id);
        if (guestRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateResourceException("Guest with email " + request.email() + " already exists");
        }
        if (guestRepository.existsByDocumentNumberAndIdNot(request.documentNumber(), id)) {
            throw new DuplicateResourceException("Guest with this document number already exists");
        }
        guestMapper.updateEntity(guest, request);
        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Override
    public void deleteGuest(Long id) {
        Guest guest = findGuestOrThrow(id);
        guestRepository.delete(guest);
    }

    private Guest findGuestOrThrow(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id));
    }
}
