package com.senla.internship.caveatemptor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private String name;
    // insertable = false
    private BigDecimal initialPrice;
    private BigDecimal buyNowPrice;
    // 2023-01-26 17:16:56.000000
    private Date auctionEnd;
    private double metricWeight;
}
