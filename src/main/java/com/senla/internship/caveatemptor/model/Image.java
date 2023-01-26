package com.senla.internship.caveatemptor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;
    private String title;
    private String filename;
}
