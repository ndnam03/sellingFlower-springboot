package com.vnstart.library.service.impl;

import com.vnstart.library.model.OrderDetail;
import com.vnstart.library.repository.OrderDetailRepository;
import com.vnstart.library.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    public List<OrderDetail> getAll() {
        return orderDetailRepository.findAll();
    }
}
