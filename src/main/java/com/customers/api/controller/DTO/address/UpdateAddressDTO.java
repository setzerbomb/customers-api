package com.customers.api.controller.DTO.address;

import com.customers.api.common.enums.State;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateAddressDTO {
    private State state;
    private String city;
    private String neighborhood;
    private String zipCode;
    private String street;
    @Min(0)
    private Integer number;
    private String additionalInformation;
    private Boolean main;
}
