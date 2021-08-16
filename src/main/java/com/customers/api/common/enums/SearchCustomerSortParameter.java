package com.customers.api.common.enums;

public enum SearchCustomerSortParameter {

    CUSTOMER_NAME("c.name"), CUSTOMER_BIRTH_DATE("c.birthDate"), CUSTOMER_CREATED_AT("c.createdAt"),
    ADDRESS_STATE("a.state"), ADDRESS_CITY("a.city");

    private String sorter;

    SearchCustomerSortParameter(String sorter) {
        this.sorter = sorter;
    }

    public String getSorter() {
        return sorter;
    }
}
