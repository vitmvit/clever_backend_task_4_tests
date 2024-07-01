package ru.clevertec.product.constant.util;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static ru.clevertec.product.constant.Constant.*;

@Builder(setterPrefix = "with")
@Data
public class ProductTestData {

    @Builder.Default
    private UUID uuid = PRODUCT_UUID;

    @Builder.Default
    private String name = PRODUCT_NAME;

    @Builder.Default
    private String description = PRODUCT_DESCRIPTION;

    @Builder.Default
    private BigDecimal price = PRODUCT_PRICE;

    @Builder.Default
    private LocalDateTime created = PRODUCT_DATE_CREATED;

    public Product buildProduct() {
        return new Product(uuid, name, description, price, created);
    }

    public Product buildUpdateProduct() {
        return new Product(uuid, name, description + " new", price.add(BigDecimal.valueOf(2)), created);
    }

    public ProductDto buildProductDto() {
        return new ProductDto(name, description, price);
    }

    public InfoProductDto buildInfoProductDto() {
        return new InfoProductDto(uuid, name, description, price);
    }
}