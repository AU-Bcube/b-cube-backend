package com.b_cube.website.domain.study.exception;

public class StudyNotFoundException extends RuntimeException {
    public StudyNotFoundException(String message) {
        super(message);
    }
}
