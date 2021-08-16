package com.customers.api.model.services.customer;

import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.common.utils.Transfer;
import com.customers.api.controller.DTO.address.UpdateAddressDTO;
import com.customers.api.controller.DTO.customer.UpdateCustomerDTO;
import com.customers.api.model.entities.Address;
import com.customers.api.model.entities.Customer;
import com.customers.api.model.repositories.AddressRepository;
import com.customers.api.model.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateCustomerService {

    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    public Mono<Customer> update(Long id, UpdateCustomerDTO updateCustomerDTO) {
        return this.find(id).flatMap(customer -> {
            try {
                new Transfer<UpdateCustomerDTO, Customer>().transferDTOToEntity(updateCustomerDTO, customer);
                customer.setUpdatedAt(LocalDateTime.now());
            } catch (Exception e) {
                return Mono.error(new ApiErrorException(HttpStatus.NOT_FOUND, "Erro interno no servidor", "update_customer"));
            }
            var k = customerRepository.save(customer)
                    .flatMap(c -> {
                        c.setAddresses(new ArrayList<>());

                        return addressRepository.findByCustomer(c.getId()).doOnNext(address -> {
                            if (address.getMain()){
                                updateCustomerDTO.getMainAddress().setMain(true);
                                try {
                                    new Transfer<UpdateAddressDTO, Address>().transferDTOToEntity(updateCustomerDTO.getMainAddress(), address);
                                }catch (Exception e) {
                                    log.info(e.getMessage());
                                }
                                c.setMainAddress(address);
                                c.getMainAddress().setUpdatedAt(LocalDateTime.now());
                            }
                            c.getAddresses().add(address);
                        }).then(Mono.just(c));
                    })
                    .flatMap(c -> addressRepository.save(c.getMainAddress()).map(a -> {
                        c.setMainAddress(a);
                        return c;
                    }));
            return k;
        });
    }

    public Mono<Customer> find(Long id){
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new ApiErrorException(HttpStatus.NOT_FOUND, "Cliente não encontrado", "update_customer")));
    }

}
