package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<UUID, Product> productMap = Map.of(
            UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002"), new Product(UUID.fromString("2b4b6ef8-742c-11ee-b962-0242ac120002"), "Яблоко", "Вкусное", BigDecimal.valueOf(1.01), LocalDateTime.MIN)
    );

    @Override
    public Optional<Product> findById(UUID uuid) {
        return Optional.ofNullable(productMap.get(uuid));
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }
}
