package com.customers.api.common.components;

import com.customers.api.model.entities.Customer;
import com.customers.api.model.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
@AllArgsConstructor
@Log4j2
public class PopulateCustomerObject {

    private AddressRepository addressRepository;

    public Mono<Customer> fill(Customer customer){
        customer.setAddresses(new ArrayList<>());
        return addressRepository.findByCustomer(customer.getId())
                .doOnNext(address -> {
                    if (address.getMain()){
                        customer.setMainAddress(address);
                    }
                    customer.getAddresses().add(address);
                }).then(Mono.just(customer));
    }
}
