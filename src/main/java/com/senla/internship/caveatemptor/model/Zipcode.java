package com.senla.internship.caveatemptor.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class Zipcode {
    protected String value;

    public Zipcode(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zipcode zipcode = (Zipcode) o;
        return Objects.equals(value, zipcode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
