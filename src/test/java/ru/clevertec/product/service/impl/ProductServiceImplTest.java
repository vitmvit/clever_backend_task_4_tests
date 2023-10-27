package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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

    @Test
    void createShouldInvokeRepositoryWithoutProjectUUID() {
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
    void delete() {
        // given
        UUID uuid = ProductTestData.builder().build().getUuid();

        // when
        productService.delete(uuid);

        // then
        verify(productRepository).delete(uuid);
    }
}
