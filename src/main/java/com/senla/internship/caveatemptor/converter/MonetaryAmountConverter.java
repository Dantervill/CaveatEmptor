package com.senla.internship.caveatemptor.converter;

import com.senla.internship.caveatemptor.model.advanced.MonetaryAmount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/*
    При сохранении данных экземпляр MonetaryAmount необходимо преобразовать
    в объект String с представлением "<amount> <currency suffix>".
    При загрузке данных объект String нужно преобразовать обратно
    в экземпляр MonetaryAmount.
 */

// Автоматически применяется к полям типа MonetaryAmount.
@Converter(autoApply = true)
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String> {

    @Override
    public String convertToDatabaseColumn(MonetaryAmount attribute) {
        return attribute.toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String dbData) {
        return MonetaryAmount.fromString(dbData);
    }
}
