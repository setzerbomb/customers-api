package com.customers.api.model.services.address;

import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.model.entities.Address;
import com.customers.api.model.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class FindAddressService {

    private AddressRepository addressRepository;

    public Mono<Address> find(Long customer,Long id) {
        return addressRepository.findByCustomerAndId(customer,id)
                .switchIfEmpty(Mono.error(new ApiErrorException(HttpStatus.NOT_FOUND, "Endereço não encontrado", "find_address")));
    }
}
