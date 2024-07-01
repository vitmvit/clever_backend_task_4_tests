package ru.clevertec.product.repository.impl;

import ru.clevertec.product.connection.DbConnection;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ConnectionException;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.exception.SqlExecuteException;
import ru.clevertec.product.repository.ProductRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {

    private final Optional<Connection> connection;

    public InMemoryProductRepository() {
        this.connection = new DbConnection().getConnection();
    }

    @Override
    public Optional<Product> findById(UUID uuid) {
        if (connection.isPresent()) {
            String sql = "SELECT uuid, name, description, price, created FROM product WHERE uuid = ?";
            try (var preparedStatement = connection.get().prepareStatement(sql)) {
                preparedStatement.setString(1, uuid.toString());
                var resultSet = preparedStatement.executeQuery();
                var list = getAllFromResultSet(resultSet);
                return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
            } catch (SQLException ex) {
                throw new SqlExecuteException(ex);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public List<Product> findAll() {
        if (connection.isPresent()) {
            String sql = "SELECT uuid, name, description, price, created FROM product";
            try (var preparedStatement = connection.get().prepareStatement(sql)) {
                var resultSet = preparedStatement.executeQuery();
                return getAllFromResultSet(resultSet);
            } catch (SQLException ex) {
                throw new SqlExecuteException(ex);
            }
        }
        throw new ConnectionException();
    }

    @Override
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is null");
        }
        if (connection.isPresent()) {
            return product.getUuid() == null
                    ? create(product)
                    : update(product);
        }
        throw new ConnectionException();
    }

    @Override
    public void delete(UUID uuid) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM product WHERE uuid = ?";
            try (var preparedStatement = connection.get().prepareStatement(sql)) {
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new SqlExecuteException(ex);
            }
        } else {
            throw new ConnectionException();
        }
    }

    private List<Product> getAllFromResultSet(ResultSet resultSet) {
        try {
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                var product = Product
                        .builder()
                        .uuid(UUID.fromString(resultSet.getString("uuid")))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getBigDecimal("price"))
                        .created(resultSet.getTimestamp("created").toLocalDateTime())
                        .build();
                productList.add(product);
            }
            return productList;
        } catch (SQLException ignored) {
        }
        return List.of();
    }

    private Product create(Product product) {
        String sql = "INSERT INTO product (uuid, name, description, price, created) VALUES (?, ?, ?, ?, ?)";
        UUID uuid = UUID.randomUUID();
        try (var preparedStatement = connection.get().prepareStatement(sql)) {
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setBigDecimal(4, product.getPrice());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(product.getCreated()));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SqlExecuteException(ex);
        }
        return findById(uuid).orElseThrow(
                () -> new ProductNotFoundException(uuid)
        );
    }

    private Product update(Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, created = ? WHERE uuid = ?";
        Optional<Product> exists = findById(product.getUuid());
        if (exists.isPresent()) {
            try (var preparedStatement = connection.get().prepareStatement(sql)) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setBigDecimal(3, product.getPrice());
                preparedStatement.setTimestamp(4, Timestamp.valueOf(product.getCreated()));
                preparedStatement.setString(5, product.getUuid().toString());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new SqlExecuteException(ex);
            }
        } else {
            throw new ProductNotFoundException(product.getUuid());
        }
        return findById(product.getUuid()).orElseThrow(
                () -> new ProductNotFoundException(product.getUuid())
        );
    }
}