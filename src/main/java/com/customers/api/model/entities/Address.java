package com.customers.api.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("addresses")
public class Address implements Serializable {

    @Id
    private Long id;
    private String state;
    private String city;
    private String neighborhood;
    @Column("zipcode")
    private String zipCode;
    private String street;
    private Integer number;
    @Column("additionalinformation")
    private String additionalInformation;
    @Builder.Default
    private Boolean main = Boolean.FALSE;
    @JsonIgnore
    private Long customer;
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
