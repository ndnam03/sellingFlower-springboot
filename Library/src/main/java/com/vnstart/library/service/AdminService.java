package com.vnstart.library.service;

import com.vnstart.library.dto.AdminDto;
import com.vnstart.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
