package com.neightec.neightecavserver.models.enums;

public enum WalletTransactionTypeEnum {
    EINKOMMEN("Einnahmen"),
    AUSGABE("Ausgaben"),
    ERSPARNIS("Ersparnisse");

    private String enumName;

    WalletTransactionTypeEnum(String enumName) {
        this.enumName = enumName;
    }

    public String getWalletTransactionTypeName() {
        return enumName;
    }
}
