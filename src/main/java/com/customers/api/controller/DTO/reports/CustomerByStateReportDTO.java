package com.customers.api.controller.DTO.reports;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerByStateReportDTO {
    String state;
    Integer quantity;
}
