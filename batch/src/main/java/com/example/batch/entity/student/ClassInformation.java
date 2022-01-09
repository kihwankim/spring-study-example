package com.example.batch.entity.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class ClassInformation {

    private String teacherName;
    private int studentSize;

    public ClassInformation(String teacherName, int studentSize) {
        this.teacherName = teacherName;
        this.studentSize = studentSize;
    }
}
