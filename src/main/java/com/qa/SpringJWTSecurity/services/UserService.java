package com.qa.SpringJWTSecurity.services;

import com.qa.SpringJWTSecurity.dtos.user.ProfileDTO;
import com.qa.SpringJWTSecurity.entities.user.User;
import com.qa.SpringJWTSecurity.repos.UserRepo;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class UserService {
    private final UserRepo repo;
    private ModelMapper mapper;

    public UserService(ModelMapper mapper, UserRepo repo) {
        super();
        this.mapper = mapper;
        this.repo = repo;
    }

    public ProfileDTO getProfileByEmail(String email) {
        Optional<User> savedUser = this.repo.findByEmail(email);
        return this.mapper.map(savedUser, ProfileDTO.class);
    }
}
