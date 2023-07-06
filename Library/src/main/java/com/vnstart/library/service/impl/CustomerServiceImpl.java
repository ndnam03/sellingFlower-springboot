package com.vnstart.library.service.impl;

import com.vnstart.library.dto.CustomerDto;
import com.vnstart.library.model.Customer;
import com.vnstart.library.repository.CustomerRepository;
import com.vnstart.library.repository.RoleRepository;
import com.vnstart.library.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        Customer customerSave = customerRepository.save(customer);
        return mapperDTO(customerSave);
    }

    @Override
    public Customer saveInfo(Customer customer) {
        Customer customer1 = customerRepository.findByUsername(customer.getUsername());
        customer1.setAddress(customer.getAddress());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        return customerRepository.save(customer1);
    }


    public CustomerDto mapperDTO(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPassword(customer.getPassword());
        customerDto.setUsername(customer.getUsername());
        return customerDto;
    }
}
