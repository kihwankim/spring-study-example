package com.example.batch.jobparameter;

import com.example.batch.entity.product.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Getter
@NoArgsConstructor
public class CreateDateJobParameter {
    private LocalDate createDate;

    @Value("#{jobParameters[status]}")
    private ProductStatus status;

    @Value("#{jobParameters[createDate]}")
    public void setCreateDate(String createDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.createDate = LocalDate.parse(createDate, formatter);
    }

}
