package com.customers.api.model.repositories;

import com.customers.api.model.entities.Address;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AddressRepository extends ReactiveCrudRepository<Address, Long> {
    Flux<Address> findByCustomer(Long customer);

    Mono<Address> findByCustomerAndId(Long id, Long customer);

    Mono<Void> deleteByCustomerAndId(Long id, Long customer);

    Mono<Void> deleteAllByCustomer(Long customer);

    Mono<Address> findByCustomerAndMain(Long customer, Boolean main);
}
