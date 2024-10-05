package org.othertwink.onlineshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.othertwink.onlineshop.model.entity.Customer;
import org.othertwink.onlineshop.repository.CustomerRepo;
import org.othertwink.onlineshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    @Transactional
    public Customer createCustomer(String firstName, String lastName, String email, String contactNumber) {
        Customer createdCustomer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .contactNumber(contactNumber)
                .build();
        return customerRepo.save(createdCustomer);
    }

    @Override
    @Transactional
    public Customer deleteCustomer(Long customerId) {
        Customer customer = findById(customerId);
        customerRepo.delete(customer);
        return customer;
    }

    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {
        Customer updCustomer = findById(customer.getCustomerId());
        updCustomer.setFirstName(customer.getFirstName());
        updCustomer.setLastName(customer.getLastName());
        updCustomer.setEmail(customer.getEmail());
        updCustomer.setContactNumber(customer.getContactNumber());
        return customerRepo.save(updCustomer);
    }

    @Transactional
    public Customer findById(Long customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("No customer under Id " + customerId + " found"));
    }

}
