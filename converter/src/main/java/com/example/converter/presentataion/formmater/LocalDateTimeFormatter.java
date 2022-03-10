package com.example.converter.presentataion.formmater;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    private static final String DEFAULT_LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(DEFAULT_LOCAL_DATE_TIME_PATTERN);

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, DATETIME_FORMAT);
    }

    @Override
    public String print(LocalDateTime object, Locale locale) {
        return object.toString();
    }
}
