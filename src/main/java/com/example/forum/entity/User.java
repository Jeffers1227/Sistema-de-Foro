package com.example.forum.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Data @NoArgsConstructor
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // CAMBIO AQUÍ: Usamos MERGE en lugar de ALL para evitar el error "detached entity"
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE) 
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new HashSet<>();

    public String getAvatarUrl() {
        // Usamos la API gratuita de DiceBear. 
        // Genera una imagen única basada en el username.
        return "https://api.dicebear.com/7.x/identicon/svg?seed=" + this.username;
    }

}