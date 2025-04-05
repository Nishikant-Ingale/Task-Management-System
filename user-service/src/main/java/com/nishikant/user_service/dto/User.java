package com.nishikant.user_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
