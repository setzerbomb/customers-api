package com.customers.api.model.services.customer;

import com.customers.api.common.components.PopulateCustomerObject;
import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.model.entities.Customer;
import com.customers.api.model.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class FindCustomerService {

    private CustomerRepository customerRepository;
    private PopulateCustomerObject populateCustomerObject;

    public Mono<Customer> find(Long id){
        return customerRepository.findById(id)
                .flatMap(populateCustomerObject::fill)
                .switchIfEmpty(Mono.error(new ApiErrorException(HttpStatus.NOT_FOUND, "Cliente não encontrado", "find_customer")));
    }


}
