package com.ursmahesh.securex.util;

import java.util.Base64;

public class ImageUtil {

    /**
     * Convert byte array to Base64 string
     */
    public static String encodeToBase64(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * Convert Base64 string to byte array
     */
    public static byte[] decodeFromBase64(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            return null;
        }
        return Base64.getDecoder().decode(base64Image);
    }
}
