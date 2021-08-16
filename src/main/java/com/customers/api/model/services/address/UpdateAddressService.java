package com.customers.api.model.services.address;

import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.common.utils.Transfer;
import com.customers.api.controller.DTO.address.UpdateAddressDTO;
import com.customers.api.model.entities.Address;
import com.customers.api.model.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static java.util.Objects.nonNull;

@Repository
@AllArgsConstructor
@Log4j2
public class UpdateAddressService {

    private AddressRepository addressRepository;

    public Mono<? extends Address> update(Long customer,Long id, UpdateAddressDTO updateAddressDTO) {

        return addressRepository.findByCustomerAndId(customer,id)
                .flatMap(address -> {
                    try {
                        new Transfer<UpdateAddressDTO, Address>().transferDTOToEntity(updateAddressDTO, address);
                    }catch (Exception e) {
                        log.info(e.getMessage());
                    }
                    if (nonNull(updateAddressDTO.getMain()) && updateAddressDTO.getMain()) {
                        return addressRepository.findByCustomerAndMain(customer,true)
                                .flatMap(a -> {
                                    a.setMain(false);
                                    return addressRepository.save(a).then(addressRepository.save(address));
                                });
                    }
                    return addressRepository.save(address);
                })
                .switchIfEmpty(Mono.error(new ApiErrorException(HttpStatus.NOT_FOUND, "Endereço não encontrado", "update_address")));
    }
}
