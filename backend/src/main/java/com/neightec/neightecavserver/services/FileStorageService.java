package com.neightec.neightecavserver.services;

import com.neightec.neightecavserver.config.NeightecFilePathsConfig;
import com.neightec.neightecavserver.models.enums.FileStorageTypeEnum;
import com.neightec.neightecavserver.models.enums.GuestAttendanceEnum;
import com.neightec.neightecavserver.models.neightec_data.FileSignature;
import com.neightec.neightecavserver.models.neightec_data.Guest;
import com.neightec.neightecavserver.repositories.FileSignatureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * general service to extract objects from file storage file
 * @author natanielsusantoputra
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FileStorageService {

    private final GuestService guestService;

    public Boolean uploadFile(MultipartFile file) {
        if (file != null) {
            log.info("upload file called: {}", file.getOriginalFilename());
            String content = checkContentType(file.getContentType());
            StringBuilder stringBuilder = null;
            if (Objects.equals(content, FileStorageTypeEnum.CSV.getName())) {
                stringBuilder = loadFile(file);
            }

            if (stringBuilder != null) {
                insertEntriesIntoTable(stringBuilder);
                return true;
            }
        }
        return false;
    }

    private String checkContentType(String contentType) {
        switch (contentType) {
            case "text/csv":
                return FileStorageTypeEnum.CSV.getName();
            case "text/xlsx":
               return FileStorageTypeEnum.XLSX.getName();
        }
        return null;
    }

    private static StringBuilder loadFile(MultipartFile file) {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            reader.readLine();
            String line;
            for (line = reader.readLine(); line != null; line = reader.readLine()) {
                sb.append(line);
                sb.append("%");
            }
            log.info("file " + file.getOriginalFilename() + " read.");
            return sb;
        } catch (Exception e) {
            log.error("CSV name \"" + file.getOriginalFilename() + "\" to can not be read", e);
        }
        return null;
    }

    public void insertEntriesIntoTable(StringBuilder sb) {
        try {
            List<String> entries;

            if (sb != null) {
                entries = Arrays.stream(sb.toString().split("%"))
                        .collect(Collectors.toList());

                if (entries != null) {

                    for (String line : entries) {
                        String[] entry = !line.isBlank() ? line.split(";") : null;
                        String name = entry != null ? entry[0] : null;
                        if (checkGuestUnique(name)) {
                            Guest guest = new Guest();
                            guest.setFullName(name);
                            guest.setValidStart(Instant.now());
                            guest.setAttendanceStatus(GuestAttendanceEnum.ATTENDING.getName());
                            guestService.saveGuest(guest);
                            log.info("Added into dashboard: {}",  name);
                        } else {
                            log.info("Can't add due to duplication into dashboard: {}",  name);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Insert to client externals table contains error", e);
        }
    }

    private Boolean checkGuestUnique(String name) {
        return guestService.isGuestFullNameUnique(name);
    }
}
