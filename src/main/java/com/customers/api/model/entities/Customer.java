package com.customers.api.model.entities;

import com.customers.api.common.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("customers")
public class Customer implements Serializable {

    @Id
    private Long id;
    private UUID uuid;
    private String name;
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;
    private String cpf;
    private String gender;
    @Transient
    private Address mainAddress;
    @Transient
    private List<Address> addressList;
    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Version
    private Long version;
}
