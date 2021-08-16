package com.customers.api.model.services.address;

import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.controller.DTO.address.CreateAddressDTO;
import com.customers.api.model.entities.Address;
import com.customers.api.model.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static java.util.Objects.nonNull;

@Repository
@AllArgsConstructor
public class StoreAddressService {

    private AddressRepository addressRepository;

    public Mono<Address> store(Long customer, CreateAddressDTO createAddressDTO) {
        return addressRepository.findByCustomerAndMain(customer, true)
                .flatMap(a -> {
                    var address = addressRepository.save(Address.builder()
                            .state(createAddressDTO.getState().getState())
                            .city(createAddressDTO.getCity())
                            .neighborhood(createAddressDTO.getNeighborhood())
                            .zipCode(createAddressDTO.getZipCode())
                            .street(createAddressDTO.getStreet())
                            .customer(customer)
                            .main(createAddressDTO.getMain())
                            .build());
                    if (nonNull(createAddressDTO.getMain()) && createAddressDTO.getMain()) {
                        a.setMain(false);
                        return addressRepository.save(a).then(address);
                    }
                    return address;
                })
                .switchIfEmpty(addressRepository.save(Address.builder()
                        .state(createAddressDTO.getState().getState())
                        .city(createAddressDTO.getCity())
                        .neighborhood(createAddressDTO.getNeighborhood())
                        .zipCode(createAddressDTO.getZipCode())
                        .street(createAddressDTO.getStreet())
                        .customer(customer)
                        .main(true)
                        .build()));
    }
}
