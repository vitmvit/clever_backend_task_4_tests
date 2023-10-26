package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.util.ProductTestData;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.clevertec.product.constant.Constant.PRODUCT_UUID;
import static ru.clevertec.product.util.ProductTestData.builder;

public class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @Test
    void findByIdShouldReturnExpectedProductWithUUID() {
        // given
        ProductTestData expected = builder().build();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithName() {
        // given
        ProductTestData expected = builder().build();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithDescription() {
        // given
        ProductTestData expected = builder().build();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithPrice() {
        // given
        ProductTestData expected = builder().build();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithCreated() {
        // given
        ProductTestData expected = builder().build();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }

    @Test
    void findByIdShouldReturnExpectedProductEqualsWithoutUUID() {
        // given
        ProductTestData expected = builder().build();

        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }

    @Test
    void findByIdShouldReturnOptionalEmpty() {
        // given
        UUID uuid = UUID.fromString(PRODUCT_UUID);
        Optional<Product> expected = Optional.empty();

        // when
        Optional<Product> actual = productRepository.findById(uuid);

        // then
        assertEquals(expected, actual);
    }
}
