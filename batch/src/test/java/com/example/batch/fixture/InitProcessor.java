package com.example.batch.fixture;

import com.example.batch.entity.student.Teacher;
import com.example.batch.entity.student.TeacherRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitProcessor {
    private final TeacherRepository teacherRepository;

    public InitProcessor(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public void saveTeacher() {
        for (int i = 0; i < 30; i++) {
            teacherRepository.save(Teacher.builder()
                    .name("name" + i)
                    .subject("subject" + i)
                    .build()
            );
        }
    }
}