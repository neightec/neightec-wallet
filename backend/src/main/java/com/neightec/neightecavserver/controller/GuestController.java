package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.GuestDTO;
import com.neightec.neightecavserver.models.enums.GuestAttendanceEnum;
import com.neightec.neightecavserver.services.FileStorageService;
import com.neightec.neightecavserver.services.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guest")
@Log4j2
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    private final FileStorageService fileStorageService;

    /**
     * unlikely to use for MVP, just to prepare
     * @param uuid
     * @return
     */
    @GetMapping(
            value = {"/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestDTO> getGuestWeddingDashboard(
            @RequestParam(name = "uuid") UUID uuid) {
        log.info("get Wedding");
        GuestDTO dto = new GuestDTO("Test", GuestAttendanceEnum.ABSENT.getName(), Instant.now());
        return ResponseEntity.ok(dto);
    }

    /**
     * REST get to fetch list of guests from specific user
     * @return list of guestWedding
     */
    @GetMapping(value = "/get-dashboard-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GuestDTO>> getGuestDashboardList() {
        log.info("/get-dashboard-list called");
        List<GuestDTO> guestDTOS = guestService.getAllGuests();
        return ResponseEntity.ok(guestDTOS);
    }

    /**
     * REST post to upload list of guests from specific user
     * @param name from user
     * @param file uploaded file from user
     * @return list of guestWedding
     */
    @PostMapping(value = "/add-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GuestDTO>> addGuestDashboardList(@RequestParam ("name") String name,
                                                                @RequestParam("file") MultipartFile file) {
        log.info("Add list for Guest Wedding Dashboard from File");
        return null;
    }

    /**
     * REST post to upload list of guests from specific user
     * @param guests list of guest
     * @return list of guestWedding
     */
    @PostMapping(value = "/add-list-manual", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GuestDTO>> addGuestDashboardListManual(@RequestBody List<String> guests) {
        log.info("Add list manually for Guest Wedding Dashboard from File {}", guests);
        return ResponseEntity.ok(guestService.addGuests(guests));
    }

    /**
     * REST post to upload list of guests from specific user
     * @param files for uploading
     * @return list of guestWedding
     */
    @PostMapping(value = "/add-list-upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addGuestDashboardListViaUpload(@RequestParam("files") List<MultipartFile> files) {
        files.forEach(file -> {
            log.info("Received file: {}", file.getOriginalFilename());
            fileStorageService.uploadFile(file);
        });
        return ResponseEntity.ok("File read to upload some guests");
    }

    /**
     * REST get to find the guest by full name
     * @param fullName name of the guest
     * @return boolean response entity
     */
    @GetMapping(value = "/is-guest-full-name-unique", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isGuestFullNameUnique(@RequestParam ("full_name") String fullName) {
        log.info("/is-guest-full-name-unique called: {}", fullName);
        return ResponseEntity.ok(guestService.isGuestFullNameUnique(fullName));
    }

    /**
     * REST delete guests by fullnames
     * @param fullNames name of the guest
     * @return boolean response entity
     */
    @PostMapping(value = "/delete-guests-by-fullnames", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GuestDTO>> deleteGuestsByFullnames(@RequestBody List<String> fullNames) {
        log.info("/delete-guests-by-fullnames called: {}", fullNames);
        return ResponseEntity.ok(guestService.deleteGuestsByNames(fullNames));
    }
}
