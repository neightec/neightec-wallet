package com.neightec.neightecavserver.controller.cron;

import com.neightec.neightecavserver.services.FileSignatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/cron")
@Log4j2
@RequiredArgsConstructor
public class NeightTecAvCronController {

    private final FileSignatureService fileSignatureService;
    @GetMapping(value = "/update-file-signatures", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Scheduled(cron = "*/5 * * * * ?", zone = "Europe/Zurich")
    public ResponseEntity<String> updateFileSignatures() throws IOException {
        log.info("/cron/update-file-signatures called");
        fileSignatureService.extractFileSignatureFromXLSX();
        return ResponseEntity.ok("update-file-signatures controller");
    }
}
