package com.example.converter.presentataion.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DataRequest {
    private LocalDate date;
    private LocalDateTime datetime;
}
