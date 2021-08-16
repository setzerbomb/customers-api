package com.customers.api.model.services.reports;

import com.customers.api.controller.DTO.reports.CustomerByStateReportDTO;
import com.customers.api.model.mapper.CustomerByStateReportMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@AllArgsConstructor
@Service
public class CustomerByStateReportService {

    private R2dbcEntityTemplate r2dbcEntityTemplate;

    public Flux<CustomerByStateReportDTO> report(LocalDate startAt, LocalDate endAt){

        Integer counter = 1;

        List<Pair<String,?>> pairs = new ArrayList<>();

        String queryString = "SELECT a.state as report_state, count(c.id) as report_quantity " +
                "FROM customers AS c " +
                "INNER JOIN addresses as a " +
                "ON c.id = a.customer " +
                "where 1=1 ";

        if (nonNull(startAt)) {
            queryString += String.format("AND c.birthdate >= $%s ",counter);
            pairs.add(Pair.of(String.format("$%s",counter),startAt));
            counter++;
        }

        if (nonNull(endAt)) {
            queryString += String.format("AND c.birthdate <= $%s ",counter);
            pairs.add(Pair.of(String.format("$%s",counter),endAt));
            counter++;
        }

        queryString += "GROUP BY a.state ORDER BY  report_quantity, report_state";

        CustomerByStateReportMapper customerByStateReportMapper = new CustomerByStateReportMapper();

        var genericExecuteSpec = this.r2dbcEntityTemplate.getDatabaseClient().sql(queryString);

        for (Pair<String,?> pair: pairs) {
            genericExecuteSpec = genericExecuteSpec.bind(pair.getFirst(),pair.getSecond());
        }

        return genericExecuteSpec
                .map(customerByStateReportMapper::apply)
                .all();
    }
}
