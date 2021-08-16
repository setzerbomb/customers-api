package com.customers.api.model.services.customer;

import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.model.entities.Customer;
import com.customers.api.model.repositories.AddressRepository;
import com.customers.api.model.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class DeleteCustomerService {

    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    public Mono<Void> delete(Long id) {
        return this.find(id)
                .then(addressRepository.deleteAllByCustomer(id))
                .then(customerRepository.deleteById(id));
    }

    public Mono<Customer> find(Long id){
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ApiErrorException(HttpStatus.NOT_FOUND, "Cliente não encontrado", "delete_customer")));
    }
}
