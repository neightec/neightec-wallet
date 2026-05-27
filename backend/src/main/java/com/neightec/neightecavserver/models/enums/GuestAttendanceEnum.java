package com.neightec.neightecavserver.models.enums;

public enum GuestAttendanceEnum {
    ATTENDING("Attending"),
    ATTENDED("Attended"),
    ABSENT("Absent"),
    NOT_ATTENDED("Not Attended"),
    CANCELLED("Cancelled");
    private String name;

    GuestAttendanceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
