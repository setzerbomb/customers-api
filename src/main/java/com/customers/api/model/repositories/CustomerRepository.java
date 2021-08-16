package com.customers.api.model.repositories;

import com.customers.api.model.entities.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    Mono<Customer> findByCpf(String cpf);

    Mono<Customer> findByEmail(String email);
}
