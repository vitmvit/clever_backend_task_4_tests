package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.connection.DbConnection;
import ru.clevertec.product.constant.util.ProductTestData;
import ru.clevertec.product.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryProductRepositoryTest {

    private InMemoryProductRepository productRepository;

    @BeforeEach
    void setUp() {
        new DbConnection().getConnection();
        productRepository = new InMemoryProductRepository();
        databaseClear();
    }

    @AfterEach
    void after() {
        databaseClear();
    }

    @Test
    void findByIdShouldReturnExpectedProductWithUUID() {
        // given
        Product expected = databasePrepared();
        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithName() {
        // given
        Product expected = databasePrepared();
        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithDescription() {
        // given
        Product expected = databasePrepared();
        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithPrice() {
        // given
        Product expected = databasePrepared();
        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
    }

    @Test
    void findByIdShouldReturnExpectedProductWithCreated() {
        // given
        Product expected = databasePrepared();
        // when
        Product actual = productRepository.findById(expected.getUuid()).orElseThrow();
        // then
        assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
    }

    @Test
    void findByIdShouldReturnExpectedProductEqualsWithoutUUID() {
        // given
        Product expected = databasePrepared();
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
        UUID uuid = UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120003");
        Optional<Product> expected = Optional.empty();
        // when
        Optional<Product> actual = productRepository.findById(uuid);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void findAllShouldReturnExpectedListProducts() {
        // given
        List<Product> expectedList = List.of(
                databasePrepared()
        );
        // when
        for (Product product : expectedList) {
            productRepository.save(product);
        }
        List<Product> actualList = productRepository.findAll();
        //then
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAllShouldReturnEmptyListProducts() {
        // when
        List<Product> expectedList = productRepository.findAll();
        //then
        assertEquals(expectedList, List.of());
    }

    @Test
    void saveShouldReturnSuccessfullyAddsProductToDatabase() {
        // given
        Product expected = ProductTestData.builder().withUuid(null).build().buildProduct();
        //when and then
        assertDoesNotThrow(() -> productRepository.save(expected));
        assertNotNull(productRepository.save(expected));
    }

    @Test
    public void saveShouldThrowIllegalArgumentExceptionWhenExpectedProductNull() {
        // when and then
        assertThatThrownBy(() -> productRepository.save(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveShouldReturnSuccessfullyUpdatesProductInDatabase() {
        // given
        Product expected = databasePrepared();
        Product update = ProductTestData.builder().withUuid(expected.getUuid()).build().buildUpdateProduct();
        // when
        Product actual = productRepository.save(update);
        // then
        assertNotNull(actual);
        assertThat(actual.getUuid()).isEqualTo(update.getUuid());
        assertThat(actual.getName()).isEqualTo(update.getName());
    }

    @Test
    void delete() {
        // given
        Product product = databasePrepared();
        // when
        productRepository.delete(product.getUuid());
        Optional<Product> actual = productRepository.findById(product.getUuid());
        // then
        assertEquals(actual, Optional.empty());
    }

    private Product databasePrepared() {
        return productRepository.save(ProductTestData.builder().withUuid(null).build().buildProduct());
    }

    private void databaseClear() {
        for (Product product : productRepository.findAll()) {
            productRepository.delete(product.getUuid());
        }
    }
}