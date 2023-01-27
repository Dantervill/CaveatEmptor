package com.senla.internship.caveatemptor.model.advanced;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;


/*
    Новым требованием к системе онлайн-аукциона является применение
    нескольких валют.
 */
@Getter
@Setter
@AllArgsConstructor
// Неизменяемый класс типа-значения MonetaryAmount
/*
    Данный класс типа-значения должен реализовать интерфейс java.io.Serializable: когда
    Hibernate сохраняет данные экземпляра сущности в разделяемом кэше второго уровня,
    то он разбирает состояние сущности на составляющие. Если сущность
    имеет свойство типа MonetaryAmount, сериализованное представление значения поля со-
    храняется в кэше второго уровня. При загрузке данных сущности из кэша значение поля
    десериализуется и собирается обратно.
 */
public class MonetaryAmount implements Serializable {
    protected final BigDecimal value;
    protected final Currency currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonetaryAmount)) return false;
        final MonetaryAmount monetaryAmount = (MonetaryAmount) o;

        if (!value.equals(monetaryAmount.value)) return false;
        return currency.equals(monetaryAmount.currency);
    }

    @Override
    public int hashCode() {
        int result;
        result = value.hashCode();
        result = 29 * result + currency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getValue() + " " + getCurrency();
    }

    public static MonetaryAmount fromString(String s) {
        String[] split = s.split(" ");
        return new MonetaryAmount(new BigDecimal(split[0]), Currency.getInstance(split[1]));
    }

}
