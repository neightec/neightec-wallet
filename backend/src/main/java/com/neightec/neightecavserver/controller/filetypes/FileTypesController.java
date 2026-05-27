package com.neightec.neightecavserver.controller.filetypes;

import com.neightec.neightecavserver.models.dto.FileTypesDTO;
import com.neightec.neightecavserver.services.FileTypesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/file-type")
@Log4j2
@RequiredArgsConstructor
public class FileTypesController {

    private final FileTypesService fileTypesService;

    @GetMapping(value = "/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FileTypesDTO>> getAll() {
        log.info("get-all file-types called");
        return ResponseEntity.ok(fileTypesService.getAll());
    }
}
