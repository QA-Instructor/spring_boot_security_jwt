package com.qa.SpringJWTSecurity.entities.user;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2 , max = 50)
    private String firstName;

    @Size(min = 2 , max = 50)
    private String lastName;
    @NotNull
    @Size(min = 8)
    private String userPassword;


    @NotNull
    @Size(min = 7 , max = 50)
    @Column(unique=true)
    private String email;

    @Size(min = 11 , max = 11)
    private String phone;

    @Size(max = 300)
    @Nullable
    private String profileImage;
}
