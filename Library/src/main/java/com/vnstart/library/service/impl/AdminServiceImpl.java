package com.vnstart.library.service.impl;

import com.vnstart.library.dto.AdminDto;
import com.vnstart.library.model.Admin;
import com.vnstart.library.repository.AdminRepository;
import com.vnstart.library.repository.RoleRepository;
import com.vnstart.library.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        BeanUtils.copyProperties(adminDto,admin);

        return adminRepository.save(admin);
    }
}
