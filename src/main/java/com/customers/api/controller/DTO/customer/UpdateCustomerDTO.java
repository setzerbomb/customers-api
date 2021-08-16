package com.customers.api.controller.DTO.customer;

import com.customers.api.common.enums.Gender;
import com.customers.api.controller.DTO.address.CreateAddressDTO;
import com.customers.api.controller.DTO.address.UpdateAddressDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCustomerDTO {

    private String name;
    @Email
    private String email;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthdate;
    private String cpf;
    private Gender gender;
    private UpdateAddressDTO mainAddress;
}
