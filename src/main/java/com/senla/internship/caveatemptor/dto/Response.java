package com.senla.internship.caveatemptor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private long timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
