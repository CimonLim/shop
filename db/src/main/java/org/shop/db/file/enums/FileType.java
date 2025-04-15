package org.shop.db.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileType {
    IMAGE("IMAGE"),
    DOCUMENT("DOCUMENT"),
    VIDEO("VIDEO"),
    AUDIO("AUDIO"),
    OTHER("OTHER");

    private final String description;
}