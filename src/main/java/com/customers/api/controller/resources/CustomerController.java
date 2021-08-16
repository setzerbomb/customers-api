package com.customers.api.controller.resources;

import com.customers.api.common.enums.SearchCustomerSortParameter;
import com.customers.api.common.enums.State;
import com.customers.api.common.exceptions.ApiErrorException;
import com.customers.api.controller.DTO.customer.CreateCustomerDTO;
import com.customers.api.controller.DTO.customer.SearchCustomerDTO;
import com.customers.api.controller.DTO.customer.UpdateCustomerDTO;
import com.customers.api.model.entities.Customer;
import com.customers.api.model.services.customer.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDate;

import static java.util.Objects.nonNull;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final UpdateCustomerService updateCustomerService;
    private final StoreCustomerService storeCustomerService;
    private final SearchCostumersService searchCostumersService;
    private final FindCustomerService findCustomerService;
    private final DeleteCustomerService deleteCustomerService;

    @PostMapping
    public Mono<?> store(@Valid @RequestBody Mono<CreateCustomerDTO> createCustomerDTO){
        return createCustomerDTO
                .doOnError(e -> {
                    Mono.error(new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "create_customer"));
                })
                .flatMap(storeCustomerService::store);
    }

    @PutMapping("/{id}")
    public Mono<Customer> update(@PathVariable Long id,@Valid @RequestBody Mono<UpdateCustomerDTO> updateCustomerDTO){
        return updateCustomerDTO
                .doOnError(e -> {
                    Mono.error(new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "update_customer"));
                })
                .flatMap(dto -> updateCustomerService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return deleteCustomerService.delete(id).then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build()));
    }

    @GetMapping("/{id}")
    public Mono<Customer> find(@PathVariable Long id){
        return findCustomerService.find(id);
    }

    @GetMapping
    public Flux<Customer> search(@RequestParam(required = false) String name,
                                 @RequestParam(required = false)
                                 @DateTimeFormat(pattern = "yyyy-MM-dd")
                                         LocalDate birthdate,
                                 @RequestParam(required = false) State state,
                                 @RequestParam(required = false) String city,
                                 @RequestParam(required = false) SearchCustomerSortParameter sortParameter,
                                 @RequestParam(required = false) Sort.Direction sortOrder) {

        SearchCustomerDTO searchCustomerDTO = SearchCustomerDTO.builder()
                .name(name)
                .state(state)
                .city(city)
                .birthdate(birthdate)
                .build();

        if (nonNull(sortParameter))
            searchCustomerDTO.setSortParameter(sortParameter);
        if (nonNull(sortOrder))
            searchCustomerDTO.setSortOrder(sortOrder);

        return searchCostumersService.search(
                searchCustomerDTO
        );
    }
}
