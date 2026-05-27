package com.neightec.neightecavserver.services;

import com.neightec.neightecavserver.models.dto.GuestDTO;
import com.neightec.neightecavserver.models.enums.GuestAttendanceEnum;
import com.neightec.neightecavserver.models.neightec_data.Guest;
import com.neightec.neightecavserver.repositories.GuestRepository;
import com.neightec.neightecavserver.services.mapper.GuestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Guest Service
 * @author natanielsusantoputra
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class GuestService {

    private static final String COMMA_DELIMITER = ";";
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    // TODO update in FE store
    public List<GuestDTO> addGuests(List<String> guests) {
        if (!guests.isEmpty()) {
            List<GuestDTO> guestDTOS;
            guests.forEach(name -> {
                if (findByFullName(name) == null) {
                    Guest guest = new Guest();
                    guest.setFullName(name);
                    guest.setValidStart(Instant.now());
                    guest.setAttendanceStatus(GuestAttendanceEnum.ATTENDING.getName());
                    guestRepository.save(guest);
                }
            });
            guestDTOS = getAllGuests();
            return guestDTOS;
        }
        return Collections.emptyList();
    }

    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::toDTO).toList();
    }

    public List<GuestDTO> deleteGuestsByNames(List<String> guests) {
        if (!guests.isEmpty()) {
            guests.forEach(name -> {
                Guest guest = findByFullName(name);
                if (guest != null) {
                    log.info("Guest to be deleted: {} ", guest.getFullName());
                    deleteGuest(guest);
                }
            });
            return getAllGuests();
        }
        return Collections.emptyList();
    }

    public Guest findByFullName(String fullName) {
        return guestRepository.findByFullName(fullName);
    }

    public boolean isGuestFullNameUnique(String aktionskennzeichen) {
        return !guestRepository.existsByGuestFullName(aktionskennzeichen);
    }

    public Guest saveGuest(Guest guest) {
        return guestRepository.save(guest); 
    }
    
    @Transactional
    private void deleteGuest(Guest guest) {
        guestRepository.delete(guest);
    }
}
