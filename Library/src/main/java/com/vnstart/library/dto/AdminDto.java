package com.vnstart.library.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data @AllArgsConstructor @NoArgsConstructor
public class AdminDto {

    @Size(min = 3, max = 10,message = "Invalid first name !(3-10 charactera)")
    private String firstName;
    @Size(min = 3, max = 10,message = "Invalid last name !(3-10 charactera)")
    private String lastName;

    private String username;
    @Size(min = 5, max = 15,message = "Invalid password !(5-15 charactera)")
    private String password;

    private String repeatPassword;

}
