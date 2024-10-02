package com.qa.SpringJWTSecurity.services;

import com.qa.SpringJWTSecurity.dtos.user.LoginDTO;
import com.qa.SpringJWTSecurity.dtos.user.ProfileDTO;
import com.qa.SpringJWTSecurity.entities.user.User;
import com.qa.SpringJWTSecurity.repos.UserRepo;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@Data
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    public ProfileDTO register(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        System.out.println(user);
        Optional<User> alreadyExists = this.userRepo.findByEmail(user.getEmail());
        if (alreadyExists.isEmpty()){
            User savedUser = this.userRepo.save(user);
            return this.mapper.map(savedUser, ProfileDTO.class);
        }
        else {
            return this.mapper.map(new User(), ProfileDTO.class);
        }
    }

    public LoginDTO getProfileByEmail(String email) {
        System.out.println("Email: " + email);
        Optional<User> savedUser = this.userRepo.findByEmail(email);

        //Change for better error handling
        if (savedUser.isEmpty()) return new LoginDTO();

        return this.mapper.map(savedUser.get(), LoginDTO.class);
    }
}
