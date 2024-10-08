package com.qa.SpringJWTSecurity.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO implements Serializable {

    private Long id;

    private String profileImage;

    private String email;

    private String firstName;

    private String lastName;

    private String phone;
}
