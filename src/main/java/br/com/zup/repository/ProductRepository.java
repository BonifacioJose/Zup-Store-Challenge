package br.com.zup.repository;

import br.com.zup.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    public Optional<Product> findFirstByOrderByIdentifierDesc();
    public Optional<Product> findFirstByIdentifier(Long identifier);

}
