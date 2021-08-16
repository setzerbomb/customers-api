package com.customers.api.model.mapper;

import com.customers.api.common.enums.Gender;
import com.customers.api.model.entities.Customer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import io.r2dbc.spi.Row;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CustomerMapper implements BiFunction<Row, Object, Customer> {
    
    @Override
    public Customer apply(Row row, Object o) {
        Long id = row.get("customer_id", Long.class);
        UUID uuid = row.get("customer_uuid", UUID.class);
        String name = row.get("customer_name", String.class);
        String email = row.get("customer_email", String.class);
        LocalDate birthdate = row.get("customer_birthdate", LocalDate.class);
        String cpf = row.get("customer_cpf", String.class);
        String gender = row.get("customer_gender", String.class);
        LocalDateTime createdAt = row.get("customer_created_at", LocalDateTime.class);
        LocalDateTime updatedAt = row.get("customer_updated_at", LocalDateTime.class);
        Long version = row.get("customer_version", Long.class);

        return Customer.builder()
                .id(id)
                .uuid(uuid)
                .name(name)
                .email(email)
                .birthdate(birthdate)
                .cpf(cpf)
                .gender(gender)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .version(version)
                .build();
    }
}
