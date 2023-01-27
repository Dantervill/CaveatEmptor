package com.senla.internship.caveatemptor.model;

import com.senla.internship.caveatemptor.converter.ZipcodeConverter;
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
    @Convert(
            converter = ZipcodeConverter.class,
            attributeName = "zipcode"
    )
    protected Address homeAddress;

    @Embedded
    // Переопределение колонок еще одного встроенного класса Address
    @AttributeOverrides({
            @AttributeOverride(name = "street",
                    column = @Column(name = "BILLING_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "BILLING_ZIPCODE", length = 5)),
            @AttributeOverride(name = "city",
                    column = @Column(name = "BILLING_CITY"))
    })
    protected Address billingAddress;
}
