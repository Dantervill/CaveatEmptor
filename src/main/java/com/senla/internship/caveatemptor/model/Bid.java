package com.senla.internship.caveatemptor.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/*
    Hibernate никогда не потребуется выполнять операции UPDATE над
    таблицей BID.
 */
@Immutable
@Entity
@Table(name = "BID")
public class Bid {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    //  Свойство item позволяет осуществить переход от
    // предложения цены (Bid) к связанному товару (Item).
    // Это - ассоциация вида многие к одному: пользователи
    // могут делать несколько предложений цены для одного
    // товара.
    @ManyToOne
    protected Item item;

    private BigDecimal amount;
    private Date createdOn;

    /*
        1. Поля, имеющие аннотацию @Formula никогда не участвуют
        в SQL-операциях INSERT или UPDATE, только SELECT.
        2. База данных вычисляет SQL-выражения в формулах,
        только когда Hibernate извлекает экземпляр сущности
        из базы данных.
     */
    @Formula("(select avg(b.AMOUNT) from BID b where b.ITEM_ID = ID)")
    protected BigDecimal avgBidAmount;

//    public Bid(Item item) {
//        this.item = item;
//        item.getBids().add(this);
//    }
}
