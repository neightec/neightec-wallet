package com.neightec.neightecavserver.services;

import com.neightec.neightecavserver.config.NeightecFilePathsConfig;
import com.neightec.neightecavserver.models.neightec_data.FileSignature;
import com.neightec.neightecavserver.repositories.FileSignatureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * general service to extract objects from file signature file
 * @author natanielsusantoputra
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FileSignatureService {

    private static final String COMMA_DELIMITER = ";";
    private final FileSignatureRepository fileSignatureRepository;
    private final NeightecFilePathsConfig filePathsConfig;

    //TODO later set timer if it's already read - cronLock?
    //readfile
    //extract
    //filter
    //inject to repository
    //inject as DTOs

    public void extractFileSignatureFromCSV() throws FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        File file = ResourceUtils.getFile(filePathsConfig.getFileSignatureCsv());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
            log.info(records);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void extractFileSignatureFromXLSX() throws IOException {
        try {
            File file = ResourceUtils.getFile(filePathsConfig.getFileSignatureXlsx());
            Workbook wb = WorkbookFactory.create(file);
            Sheet sheet = wb != null ? wb.getSheetAt(0) : null;

            if (sheet != null) {
                readXlsxSheet(sheet);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readXlsxSheet(Sheet sheet) {
        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();

        for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
            if (rowNum > 1) {
                Row row = sheet.getRow(rowNum);
                addFileSignature(row);
            }
        }
    }

    @Transactional
    private void addFileSignature(Row row) {
        if (row != null) {
            FileSignature fileSignature = new FileSignature();
            fileSignature.setHexSignature(getStringCellValue(row.getCell(0)).getBytes());
            fileSignature.setIso_8859(getStringCellValue(row.getCell(1)));
            fileSignature.setExtension(getStringCellValue(row.getCell(2)));
            fileSignature.setDescription(getStringCellValue(row.getCell(3)));
            fileSignatureRepository.save(fileSignature);
        }
    }

    private String getStringCellValue(Cell cell) {
        try {
            return cell.getStringCellValue();
        } catch (IllegalStateException ignore) { }
        try {
            return "" + cell.getNumericCellValue();
        } catch (IllegalStateException ignore) { }
        return "";
    }

}
