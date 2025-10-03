package com.ehs.outsera.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WordUtils {

    public static String capitalize(String input) {
        if (input == null || input.isBlank()) return input;

        return Arrays.stream(input.trim().toLowerCase().split("\\s+"))
                .filter(s -> !s.isEmpty())
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));
    }

}
