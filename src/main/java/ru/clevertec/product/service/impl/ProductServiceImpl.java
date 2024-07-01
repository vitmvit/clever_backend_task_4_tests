package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public InfoProductDto get(UUID uuid) {
        var product = productRepository.findById(uuid).orElseThrow(() -> new ProductNotFoundException(uuid));
        return mapper.toInfoProductDto(product);
    }

    @Override
    public List<InfoProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        return products.isEmpty()
                ? List.of()
                : products.stream().map(mapper::toInfoProductDto).collect(Collectors.toList());
    }

    @Override
    public UUID create(ProductDto productDto) {
        return productRepository.save(mapper.toProduct(productDto)).getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        var product = productRepository.findById(uuid).orElseThrow(() -> new ProductNotFoundException(uuid));
        mapper.merge(product, productDto);
        productRepository.save(product);
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }
}