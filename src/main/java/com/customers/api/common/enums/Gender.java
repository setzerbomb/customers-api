package com.customers.api.common.enums;

public enum Gender {
    MASCULINO("MASCULINO"), FEMININO("FEMININO"), OUTRO("OUTRO");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
