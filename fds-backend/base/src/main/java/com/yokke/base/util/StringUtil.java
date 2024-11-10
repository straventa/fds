package com.yokke.base.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class StringUtil {

    private static final Pattern INVALID_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9_-]");

    public static String getCurrentSafeDateTimeAsString() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
        String formattedDateTime = currentDateTime.format(formatter);

        // Replace invalid characters with underscores
        String safeDateTime = INVALID_CHARS_PATTERN.matcher(formattedDateTime).replaceAll("_");

        return safeDateTime;
    }

    public static String generateThreeRandomDigits() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(3);

        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10); // Generates a random digit between 0 and 9
            sb.append(digit);
        }

        return sb.toString();
    }

}