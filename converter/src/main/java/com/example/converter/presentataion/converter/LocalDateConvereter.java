package com.example.converter.presentataion.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConvereter implements Converter<String, LocalDate> {
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DATE_FORMAT);
    }
}
