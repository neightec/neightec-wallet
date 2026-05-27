package com.neightec.neightecavserver.controller;

import com.neightec.neightecavserver.models.dto.GuestDTO;
import com.neightec.neightecavserver.models.enums.GuestAttendanceEnum;
import com.neightec.neightecavserver.models.neightec_data.NeightecUser;
import com.neightec.neightecavserver.services.FileStorageService;
import com.neightec.neightecavserver.services.GuestService;
import com.neightec.neightecavserver.services.NeightecUserService;
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
@RequestMapping("/auth")
@Log4j2
@RequiredArgsConstructor
public class AuthController {

    private final NeightecUserService neightecUserService;

    /**
     * REST delete guests by fullnames
     * @return boolean response entity
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NeightecUser> login() {
        log.info("login called!");
        return ResponseEntity.ok(neightecUserService.login());
    }
}
