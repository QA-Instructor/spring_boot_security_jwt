package com.qa.SpringJWTSecurity.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

    private String email;

    private String userPassword;
}
