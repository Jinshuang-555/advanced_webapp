package com.example.advanced_webapp.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "first name shouldn't be null")
    private String firstName;
    @NotBlank(message = "last name shouldn't be null")
    private String lastName;
    @Email(message = "invalid email address")
    private String email;
    private String password;
}
