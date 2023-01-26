package com.senla.internship.caveatemptor.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
// Название таблиц пишем всегда ПРОПИСНЫМИ буквами
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    private String username;
    private String firstname;
    private String lastname;

    @Embedded
    protected Address homeAddress;
}
