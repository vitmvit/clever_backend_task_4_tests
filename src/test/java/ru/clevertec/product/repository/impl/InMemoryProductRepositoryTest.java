package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @Test
    void findByIdShouldReturnExpectedProductWithUUID() {
        // given
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002");
        Product expected = new Product(uuid, "Яблоко", "Вкусное", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithName() {
        // given
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002");
        Product expected = new Product(uuid, "Яблоко", "Вкусное", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithDescription() {
        // given
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002");
        Product expected = new Product(uuid, "Яблоко", "Вкусное", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithPrice() {
        // given
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002");
        Product expected = new Product(uuid, "Яблоко", "Вкусное", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithCreated() {
        // given
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002");
        Product expected = new Product(uuid, "Яблоко", "Вкусное", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }

    @Test
    void findByIdShouldReturnExpectedProductEqualsWithoutUUID() {
        // given
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002");
        Product expected = new Product(uuid, "Яблоко", "Вкусное", BigDecimal.valueOf(1.01), LocalDateTime.MIN);

        // when
        Product actual = productRepository.findById(uuid).orElseThrow();

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
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120003");
        Optional<Product> expected = Optional.empty();

        // when
        Optional<Product> actual = productRepository.findById(uuid);

        // then
        assertEquals(expected, actual);
    }


//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void save() {
//    }
//
//    @Test
//    void delete() {
//    }
}
