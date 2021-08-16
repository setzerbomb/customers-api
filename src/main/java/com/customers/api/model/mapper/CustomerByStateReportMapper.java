package com.customers.api.model.mapper;

import com.customers.api.controller.DTO.reports.CustomerByStateReportDTO;
import io.r2dbc.spi.Row;

import java.util.function.BiFunction;

public class CustomerByStateReportMapper  implements BiFunction<Row, Object, CustomerByStateReportDTO> {
    @Override
    public CustomerByStateReportDTO apply(Row row, Object o) {


        String state = row.get("report_state", String.class);
        Integer quantity = row.get("report_quantity", Integer.class);

        return CustomerByStateReportDTO.builder()
                .quantity(quantity)
                .state(state)
                .build();

    }
}
