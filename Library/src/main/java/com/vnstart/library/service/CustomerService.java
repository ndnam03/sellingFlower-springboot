package com.vnstart.library.service;

import com.vnstart.library.dto.CustomerDto;
import com.vnstart.library.model.Customer;

public interface CustomerService {
    Customer findByUsername(String username);
    CustomerDto save(CustomerDto customerDto);

    Customer saveInfo(Customer customer);
}
