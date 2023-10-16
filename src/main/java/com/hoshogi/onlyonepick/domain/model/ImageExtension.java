package com.hoshogi.onlyonepick.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageExtension {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp"),
    SVG("svg"),
    HEIF("heif"),
    PBM("pbm"),
    PGM("pgm"),
    PPM("ppm"),
    CRW("crw"),
    HEIC("heic");

    private String extension;

    public static boolean containsImageExtension(String extension) {
        for (ImageExtension imageExtension : ImageExtension.values()) {
            if (imageExtension.extension.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
