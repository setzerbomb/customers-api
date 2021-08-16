package com.customers.api.controller.DTO.customer;

import com.customers.api.common.constraints.BirthDate;
import com.customers.api.common.enums.Gender;
import com.customers.api.controller.DTO.BaseDTO;
import com.customers.api.controller.DTO.address.CreateAddressDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateCustomerDTO extends BaseDTO<Long> {

    @NotEmpty(message = "Nome deve ser informado")
    @Length(max = 255)
    private String name;
    @NotEmpty(message = "Email deve ser informado")
    @Length(max = 255)
    @Email
    private String email;
    @BirthDate
    @NotNull(message = "Data de nascimento deve ser informada")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthdate;
    @NotEmpty(message = "CPF deve ser informado")
    @Length(max = 14)
    private String cpf;
    @NotNull(message = "Gênero deve ser informado")
    private Gender gender;
    @NotNull
    private CreateAddressDTO mainAddress;
}
