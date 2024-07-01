package ru.clevertec.product.mapper;

import org.junit.jupiter.api.Test;
import ru.clevertec.product.constant.util.ProductTestData;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    public void toProductTest() {
        // given
        ProductDto expected = ProductTestData.builder().build().buildProductDto();
        // when
        Product actual = productMapper.toProduct(expected);
        // then
        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.description(), actual.getDescription());
        assertEquals(expected.price(), actual.getPrice());
    }

    @Test
    public void toInfoProductDtoTest() {
        // given
        Product expected = ProductTestData.builder().build().buildProduct();
        // when
        InfoProductDto actual = productMapper.toInfoProductDto(expected);
        // then
        assertEquals(expected.getName(), actual.name());
        assertEquals(expected.getDescription(), actual.description());
        assertEquals(expected.getPrice(), actual.price());
    }

    @Test
    public void testMerge() {
        // given
        Product expected = ProductTestData.builder().build().buildProduct();
        ProductDto productDto = ProductTestData.builder().build().buildProductDto();
        // when
        Product actual = productMapper.merge(expected, productDto);
        // then
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCreated(), actual.getCreated());
    }
}