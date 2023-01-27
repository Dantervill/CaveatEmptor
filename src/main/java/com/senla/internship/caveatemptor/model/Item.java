package com.senla.internship.caveatemptor.model;

import com.senla.internship.caveatemptor.converter.MonetaryAmountConverter;
import com.senla.internship.caveatemptor.model.advanced.MonetaryAmount;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity

/* Деактивация создания SQL-выражений INSERT и UPDATE при запуске.
Активируя динамическое создание инструкций вставки и изменения, вы
сообщаете Hibernate, что строки SQL должны формироваться по требованию,
а не заранее. В этом случае инструкция UPDATE будет содержать только
столбцы с новыми значениями, а INSERT - только столбцы, которые не могут
принимать значение null.
 */
@DynamicInsert
@DynamicUpdate

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ITEM")
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(generator = "ID_GENERATOR")
    private Long id;

    @Column(name = "NAME")
    @NotNull
    @Size(
            min = 2,
            max = 255,
            message = "Name is required, maximum 255 characters."
    )
    private String name;




    /*
        Спецификация JPA требует, чтобы свойства, представляющие время, отмечались
        аннотацией @Temporal для более точного определения SQL-типов данных столбцов,
        в которые происходит отображение.
     */
    @Temporal(TemporalType.TIMESTAMP)

    /*
        Cв-во отмечено как доступное только ддя чтения, т.е.
        столбец соответствующий этому свойству, никогда не появится
        в выражениях INSERT и UPDATE: генерировать значения будет база данных.

     */
    @Column(name = "LAST_MODIFIED", insertable = false, updatable = false)

    /* ALWAYS - Hibernate будет обновлять экземпляр сущности после
       каждой SQL-операции UPDATE или INSERT. Иными словами, кто-то
       извне добавить или изменить значение этого св-ва не сможет, однако при изменении
       или обновлении записи в таблице, данное св-во обновится при помощи
       Hibernate.
     */
    @Generated(GenerationTime.ALWAYS)
    protected Date lastModified;





    // поле недоступное для SQL-операции INSERT
    @Column(name = "INITIAL_PRICE", insertable = false)
    /*
        Установка значения столбца по умолчанию.
     */
    @ColumnDefault("1.00")
    /*
        INSERT - изменение производится только после
        SQL-операции INSERT, чтобы извлечь из базы
        данных значение по умолчанию. Иными словами,
        кто-то извне не сможет совершить вставку (INSERT),
        однако это сделает Hibernate, подставив дефолтное
        значение.
     */
    @Generated(GenerationTime.INSERT)
    private BigDecimal initialPrice;

    /*
        Аннотацию @Convert можно опустить: она применяется для переопределения
        или отключения конвертера для конкретного свойства.
     */
    @Convert(
            converter = MonetaryAmountConverter.class,
            disableConversion = false
    )
    @Column(name = "PRICE", length = 63)
    private MonetaryAmount buyNowPrice;

    // Это свойство не будет храниться в базе данных
    @Transient
    private BigDecimal totalPriceIncludingTax;

    // значение даты должно быть всегда будущего времени
    @Future
    @Column(name = "AUCTION_END")
    private Date auctionEnd;

    @OneToMany
    protected Set<Bid> bids;

    @Column(name = "IMPERIAL_WEIGHT")
    /*
        При чтении - imperialWeight / 2.20462 будет выполнено в бд,
        Hibernate вернет приложению посчитанное значение.
        При записи - Hibernate подставит значение в ?, и выражение
        SQL вычислит действительное значение для вставки или изменения.
     */
    @ColumnTransformer(
            read = "IMPERIAL_WEIGHT / 2.20462",
            write = "? * 2.20462"
    )
    protected double metricWeight;
}
