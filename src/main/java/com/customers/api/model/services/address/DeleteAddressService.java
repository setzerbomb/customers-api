package com.customers.api.model.services.address;

import com.customers.api.model.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class DeleteAddressService {

    private AddressRepository addressRepository;

    public Mono<Void> delete(Long customer,Long id) {
        return addressRepository.deleteByCustomerAndId(customer,id);
    }
}
