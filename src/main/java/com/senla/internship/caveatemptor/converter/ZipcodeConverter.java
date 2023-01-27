package com.senla.internship.caveatemptor.converter;

import com.senla.internship.caveatemptor.model.GermanZipcode;
import com.senla.internship.caveatemptor.model.InvalidZipcode;
import com.senla.internship.caveatemptor.model.SwissZipcode;
import com.senla.internship.caveatemptor.model.Zipcode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ZipcodeConverter implements AttributeConverter<Zipcode, String> {

    @Override
    public String convertToDatabaseColumn(Zipcode attribute) {
        return attribute.getValue();
    }

    @Override
    public Zipcode convertToEntityAttribute(String value) {
        if (value.length() == 5) {
            return new GermanZipcode(value);
        } else if (value.length() == 4) {
            return new SwissZipcode(value);
        } else {
            return new InvalidZipcode(value);
        }
    }
}
