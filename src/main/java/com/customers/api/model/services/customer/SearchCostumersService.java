package com.customers.api.model.services.customer;

import com.customers.api.common.components.PopulateCustomerObject;
import com.customers.api.controller.DTO.customer.SearchCustomerDTO;
import com.customers.api.model.entities.Customer;
import com.customers.api.model.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class SearchCostumersService {

    private R2dbcEntityTemplate r2dbcEntityTemplate;
    private PopulateCustomerObject populateCustomerObject;

    public Flux<Customer> search(SearchCustomerDTO searchCustomerDTO){

        Integer counter = 1;

        List<Pair<String,?>> pairs = new ArrayList<>();

        String queryString = "SELECT c.id as customer_id, c.uuid as customer_uuid, c.name as customer_name, " +
                "c.email as customer_email, c.birthdate as customer_birthdate, c.cpf as customer_cpf, " +
                "c.gender as customer_gender, c.created_at as customer_created_at, c.updated_at as customer_updated_at, " +
                "c.version as customer_version " +
                "FROM customers c ";

        if (nonNull(searchCustomerDTO.getName())) {
            queryString += String.format("AND c.name = $%s ",counter);
            pairs.add(Pair.of(String.format("$%s",counter),searchCustomerDTO.getName()));
            counter++;
        }

        if (nonNull(searchCustomerDTO.getBirthdate())) {
            queryString += String.format("AND c.birthdate = $%s ",counter);
            pairs.add(Pair.of(String.format("$%s",counter),searchCustomerDTO.getBirthdate()));
            counter++;
        }
        if (nonNull(searchCustomerDTO.getCity())) {
            queryString += String.format("AND a.city = $%s ",counter);
            pairs.add(Pair.of(String.format("$%s",counter),searchCustomerDTO.getCity()));
            counter++;
        }
        if (nonNull(searchCustomerDTO.getState())) {
            queryString += String.format("AND a.state = $%s ",counter);
            pairs.add(Pair.of(String.format("$%s",counter),searchCustomerDTO.getState().getState()));
            counter++;
        }
        queryString += String.format("ORDER BY $%s ",counter) + searchCustomerDTO.getSortOrder().name();

        var genericExecuteSpec = this.r2dbcEntityTemplate.getDatabaseClient().sql(queryString);

        for (Pair<String,?> pair: pairs) {
            genericExecuteSpec = genericExecuteSpec.bind(pair.getFirst(),pair.getSecond());
        }

        CustomerMapper customerMapper = new CustomerMapper();

        return genericExecuteSpec
                .bind(String.format("$%s",counter),searchCustomerDTO.getSortParameter().getSorter())
                .map(customerMapper::apply)
                .all().flatMap(populateCustomerObject::fill);
    }

}
