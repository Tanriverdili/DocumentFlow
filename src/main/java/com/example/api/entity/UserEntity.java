package com.example.api.entity;
import com.example.api.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user-entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
