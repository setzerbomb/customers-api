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
public class CreateAddressDTO {
    @NotNull
    private State state;
    @NotEmpty
    private String city;
    @NotEmpty
    private String neighborhood;
    @NotEmpty
    private String zipCode;
    @NotEmpty
    private String street;
    @Min(0)
    private Integer number;
    private String additionalInformation;
    private Boolean main;
}
