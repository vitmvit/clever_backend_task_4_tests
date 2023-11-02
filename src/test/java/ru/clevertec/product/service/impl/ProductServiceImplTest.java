package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    static Stream<UUID> uuid() {
        return Stream.of(UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002"),
                UUID.fromString("2b4b6ef8-742c-11ee-b962-000000000000"),
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                null);
    }

    @ParameterizedTest
    @MethodSource("uuid")
    void getShouldReturnExpectedProduct(UUID uuid) {
        // given
        InfoProductDto expected = ProductTestData.builder().build().buildInfoProductDto();
        doReturn(expected).when(productMapper).toInfoProductDto(any(Product.class));
        doReturn(Optional.of(new Product())).when(productRepository).findById(uuid);
        // when
        InfoProductDto actual = productService.get(uuid);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void getShouldReturnExpectedProductWhenFound() {
        // given
        Product expected = ProductTestData.builder().build().buildProduct();
        InfoProductDto infoProductDto = ProductTestData.builder().build().buildInfoProductDto();
        UUID uuid = expected.getUuid();
        // when
        when(productRepository.findById(uuid)).thenReturn(Optional.of(expected));
        when(productMapper.toInfoProductDto(expected)).thenReturn(infoProductDto);
        InfoProductDto actual = productService.get(uuid);
        // then
        assertEquals(expected.getUuid(), actual.uuid());
        assertEquals(expected.getName(), actual.name());
        assertEquals(expected.getDescription(), actual.description());
        assertEquals(expected.getPrice(), actual.price());
    }

    @Test
    void getShouldThrowProductNotFoundExceptionWhenNotFound() {
        // when
        var exception = assertThrows(Exception.class, () -> productService.get(null));
        //then
        assertEquals(exception.getClass(), ProductNotFoundException.class);
    }

    @Test
    void findAllShouldReturnExpectedListProducts() {
        // given
        List<Product> expectedList = productRepository.findAll();
        // when
        when(productRepository.findAll()).thenReturn(expectedList);
        List<InfoProductDto> actualList = productService.getAll();
        // then
        assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    void findAllShouldThrowProductNotFoundExceptionWhenEmptyListProducts() {
        // given
        List<Product> products = new ArrayList<>();
        // when
        when(productRepository.findAll()).thenReturn(products);
        List<InfoProductDto> actualList = productService.getAll();
        // then
        assertEquals(0, actualList.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void createShouldInvokeRepositoryWithoutProductUUID() {
        // given
        Product productToSave = ProductTestData.builder().withUuid(null).build().buildProduct();
        Product expected = ProductTestData.builder().build().buildProduct();
        ProductDto productDto = ProductTestData.builder().withUuid(null).build().buildProductDto();
        // when
        doReturn(expected).when(productRepository).save(productToSave);
        when(productMapper.toProduct(productDto)).thenReturn(productToSave);
        productService.create(productDto);
        // then
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue()).hasFieldOrPropertyWithValue(Product.Fields.uuid, null);
    }

    @Test
    public void updateShouldThrowProductNotFoundExceptionWhenProductNotFound() {
        // given
        UUID uuid = ProductTestData.builder().build().getUuid();
        ProductDto productDto = ProductTestData.builder().build().buildProductDto();
        // when
        when(productRepository.findById(uuid)).thenReturn(Optional.empty());
        // then
        assertThrows(ProductNotFoundException.class, () -> productService.update(uuid, productDto));
        verify(productRepository, times(1)).findById(uuid);
    }

    @Test
    public void updateShouldCallsMergeAndSaveWhenProductFound() {
        // given
        UUID uuid = ProductTestData.builder().build().getUuid();
        ProductDto productDto = ProductTestData.builder().build().buildProductDto();
        Product product = new Product();
        // when
        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));
        productService.update(uuid, productDto);
        // then
        verify(productRepository, times(1)).findById(uuid);
        verify(productMapper, times(1)).merge(productCaptor.capture(), eq(productDto));
        assertSame(product, productCaptor.getValue());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void delete() {
        // given
        UUID uuid = ProductTestData.builder().build().getUuid();
        // when
        productService.delete(uuid);
        // then
        verify(productRepository).delete(uuid);
    }
}