package br.com.zup.repository;

import br.com.zup.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    public Optional<Order> findFirstByOrderByIdentifierDesc();
    public Optional<Order> findFirstByIdentifier(Long identifier);

}
