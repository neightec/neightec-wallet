package com.neightec.neightecavserver.models.dto;

import com.neightec.neightecavserver.models.enums.GuestAttendanceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
@Setter
public class GuestDTO {
    private String name;
    private String attendanceStatus;
    private Instant date;
}
