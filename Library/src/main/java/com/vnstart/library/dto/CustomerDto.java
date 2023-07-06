package com.vnstart.library.dto;

import lombok.*;

import javax.validation.constraints.Size;
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class CustomerDto {
    @Size(min = 3,max = 15,message = "First name should have 3-15 characters ")
    private String firstName;
    @Size(min = 3,max = 15,message = "Lats name should have 3-15 characters ")
    private String lastName;

    private String username;

    @Size(min = 5, max = 20, message = "Password should have 5-20 characters ")
    private String password;

    private String repeatPassword;
}
