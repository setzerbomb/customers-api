package com.customers.api.controller.DTO.customer;

import com.customers.api.common.enums.SearchCustomerSortParameter;
import com.customers.api.common.enums.State;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SearchCustomerDTO {
    private String name;
    private LocalDate birthdate;
    private State state;
    private String city;
    @Builder.Default
    private SearchCustomerSortParameter sortParameter = SearchCustomerSortParameter.CUSTOMER_NAME;
    @Builder.Default
    private Sort.Direction sortOrder = Sort.Direction.ASC;
}
