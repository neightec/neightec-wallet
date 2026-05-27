package com.neightec.neightecavserver.models.enums;

public enum FileStorageTypeEnum {
    CSV("CSV"),
    XLSX("XLSX");
    private String name;

    FileStorageTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
