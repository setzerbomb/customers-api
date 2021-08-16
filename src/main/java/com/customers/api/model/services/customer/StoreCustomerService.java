package com.customers.api.model.services.customer;

import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.controller.DTO.customer.CreateCustomerDTO;
import com.customers.api.model.entities.Address;
import com.customers.api.model.entities.Customer;
import com.customers.api.model.repositories.AddressRepository;
import com.customers.api.model.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class StoreCustomerService {

    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    @Transactional
    public Mono<Customer> store(CreateCustomerDTO createCustomerDTO) {

        var customer = customerRepository.findByEmail(createCustomerDTO.getEmail())
                .concatWith(customerRepository.findByCpf(createCustomerDTO.getCpf()))
                .flatMap(c -> {
                   if (nonNull(c)) {
                       return error("Cliente já existente na base de dados", HttpStatus.BAD_REQUEST);
                   }
                   return null;
                }).then(customerRepository.save(
                        Customer.builder()
                                .name(createCustomerDTO.getName())
                                .email(createCustomerDTO.getEmail())
                                .birthdate(createCustomerDTO.getBirthdate())
                                .cpf(createCustomerDTO.getCpf())
                                .gender(createCustomerDTO.getGender().name())
                                .build()
                )).flatMap(c ->
                    addressRepository.save(
                            Address.builder()
                                    .state(createCustomerDTO.getMainAddress().getState().getState())
                                    .city(createCustomerDTO.getMainAddress().getCity())
                                    .neighborhood(createCustomerDTO.getMainAddress().getNeighborhood())
                                    .zipCode(createCustomerDTO.getMainAddress().getZipCode())
                                    .street(createCustomerDTO.getMainAddress().getStreet())
                                    .customer(c.getId())
                                    .main(Boolean.TRUE)
                                    .build()
                    ).map(address -> {
                        c.setMainAddress(address);
                        c.setAddresses(List.of(address));
                        return c;
                    })
                );

        return customer;
    }

    private Mono<ApiErrorException> error(String message, HttpStatus status){
        return Mono.error(new ApiErrorException(status, message, "create_customer"));
    }


}
