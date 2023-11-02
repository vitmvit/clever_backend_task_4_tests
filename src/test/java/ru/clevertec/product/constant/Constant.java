package ru.clevertec.product.constant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

public class Constant {

    public static final UUID PRODUCT_UUID = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002");
    public static final String PRODUCT_NAME = "Яблоко";
    public static final String PRODUCT_DESCRIPTION = "Вкусное";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(1.01);
    public static final LocalDateTime PRODUCT_DATE_CREATED = LocalDateTime.of(2023, Month.OCTOBER, 26, 17, 0, 0);
}