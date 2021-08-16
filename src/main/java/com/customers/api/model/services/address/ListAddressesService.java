package com.customers.api.model.services.address;

import com.customers.api.model.entities.Address;
import com.customers.api.model.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class ListAddressesService {

    private AddressRepository addressRepository;

    public Flux<Address> list(Long customer) {
        return addressRepository.findByCustomer(customer);
    }
}
