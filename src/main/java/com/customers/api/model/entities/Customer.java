package com.customers.api.model.entities;

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
    @Builder.Default
    private UUID uuid = UUID.randomUUID();
    private String name;
    private String email;
    private LocalDate birthdate;
    private String cpf;
    private String gender;
    @Transient
    private Address mainAddress;
    @Transient
    private List<Address> addresses;
    @Column("created_at")
    @CreatedDate
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column("updated_at")
    @LastModifiedDate
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Version
    private Long version;
}
