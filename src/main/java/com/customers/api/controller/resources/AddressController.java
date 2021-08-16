package com.customers.api.controller.resources;

import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.controller.DTO.address.CreateAddressDTO;
import com.customers.api.controller.DTO.address.UpdateAddressDTO;
import com.customers.api.model.entities.Address;
import com.customers.api.model.services.address.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class AddressController {


    private final UpdateAddressService updateAddressService;
    private final StoreAddressService storeAddressService;
    private final ListAddressesService listAddressesService;
    private final FindAddressService findAddressService;
    private final DeleteAddressService deleteAddressService;

    @PostMapping("/{customer}/addresses")
    public Mono<?> store(@PathVariable Long customer, @Valid @RequestBody Mono<CreateAddressDTO> createAddressDTO){
        return createAddressDTO
                .doOnError(e -> {
                    Mono.error(new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "create_address"));
                })
                .flatMap(dto -> storeAddressService.store(customer, dto));
    }

    @PutMapping("/{customer}/addresses/{id}")
    public Mono<Address> update(@PathVariable Long customer, @PathVariable Long id, @Valid @RequestBody Mono<UpdateAddressDTO> updateAddressDTO){
        return updateAddressDTO
                .doOnError(e -> {
                    Mono.error(new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "update_address"));
                })
                .flatMap(dto -> updateAddressService.update(customer,id,dto));
    }

    @DeleteMapping("/{customer}/addresses/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long customer,@PathVariable Long id) {
        return deleteAddressService.delete(customer,id).then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }

    @GetMapping("/{customer}/addresses/{id}")
    public Mono<Address> find(@PathVariable Long customer,@PathVariable Long id){
        return findAddressService.find(customer,id);
    }

    @GetMapping("/{customer}/addresses")
    public Flux<Address> list(@PathVariable Long customer){
        return listAddressesService.list(customer);
    }
}
