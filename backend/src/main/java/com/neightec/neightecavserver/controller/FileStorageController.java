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
@RequestMapping("/filestorage")
@Log4j2
@RequiredArgsConstructor
public class FileStorageController {

    private final FileStorageService fileStorageService;

    /**
     * REST get all files from file storage
     * @return boolean
     */
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> getAllFiles() {
        log.info("get all files called");
        return ResponseEntity.ok(true);
    }

    /**
     * REST get all files from file storage
     * @return boolean
     */
    @PostMapping(value = "/upload-file", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("/upload-file called");
        return ResponseEntity.ok(fileStorageService.uploadFile(file));
    }

}
