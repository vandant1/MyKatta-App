package com.genius.mykatta.model.enums;

public enum FileType {
    PDF("Portable Document Format"),
    DOCX("Microsoft Word Document"),
    PPT("PowerPoint Presentation"),
    TXT("Plain Text File"),
    JPG("JPEG Image"),
    XML("eXtensible Markup Language");

    private final String description;

    FileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
