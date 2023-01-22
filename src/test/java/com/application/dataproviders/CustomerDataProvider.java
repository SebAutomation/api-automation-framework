package com.application.dataproviders;

import com.application.pojo.Customer;

import java.util.Map;

import static java.lang.Integer.valueOf;

public class CustomerDataProvider {

    private Customer customer;

    public CustomerDataProvider(Customer customer) {
        this.customer = customer;
    }

    public Customer createCustomer(Map<String, String> elements){
        customer = new Customer();
        customer.setId(valueOf(elements.get("id")));
        customer.setName(elements.get("name"));
        customer.setSurname(elements.get("surname"));
        customer.setAge(valueOf(elements.get("age")));
        return customer;
    }
}
