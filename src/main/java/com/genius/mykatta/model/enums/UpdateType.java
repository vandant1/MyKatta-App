package com.genius.mykatta.model.enums;

public enum UpdateType {
    ANNOUNCEMENT("Important notice for all"),
    CHAT("Peer-to-peer conversation"),
    MESSAGE("General message update");

    private final String purpose;

    UpdateType(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }
}
