package org.othertwink.onlineshop.service;

import org.othertwink.onlineshop.model.entity.Customer;

public interface CustomerService {
    Customer createCustomer(String firstName, String lastName, String email, String contactNumber);
    Customer deleteCustomer(Long customerId);
    Customer updateCustomer(Customer customer);

}
