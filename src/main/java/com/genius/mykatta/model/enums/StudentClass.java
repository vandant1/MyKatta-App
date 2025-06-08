package com.genius.mykatta.model.enums;

public enum StudentClass {
    FE("First Year Engineering"),
    SE("Second Year Engineering"),
    TE("Third Year Engineering"),
    BE("Final Year Engineering");

    private final String displayName;

    StudentClass(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
