package com.example.api.dto;
import com.example.api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    public String username;
    public String password;
    public Role role;
}