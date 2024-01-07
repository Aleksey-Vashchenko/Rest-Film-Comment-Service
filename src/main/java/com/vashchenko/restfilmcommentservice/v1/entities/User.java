package com.vashchenko.restfilmcommentservice.v1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
@Table(name = "users_table")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    @Size(min = 8, message = "Логин должен содержать не менее 8 символов")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$",
            message = "Логин должен содержать хотя бы одну заглавную латинскую букву и хотя бы одну цифру, и разрешены только латинские буквы и цифры"
    )
    private String login;
    @Column(nullable = false)
    @Size(min = 8, message = "Логин должен содержать не менее 8 символов")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!_]+$",
            message = "Логин должен содержать хотя бы одну заглавную латинскую букву и хотя бы одну цифру, разрешены только латинские буквы, цифры, восклицательный знак и нижнее подчеркивание"
    )
    private String password;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private boolean isEnabled;
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles ;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
