package pl.pollub.hotel_management_project.guest;

import java.util.List;

public interface GuestService {

    GuestResponse createGuest(GuestRequest request);

    GuestResponse getGuestById(Long id);

    List<GuestResponse> getGuests(String lastName);

    GuestResponse updateGuest(Long id, GuestRequest request);

    void deleteGuest(Long id);
}
