package com.vashchenko.restfilmcommentservice.v1.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.vashchenko.restfilmcommentservice.v1.configs.UserJsonViews;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "roles")
@EqualsAndHashCode(exclude = "roles")
@Table(name = "users_table")
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({UserJsonViews.AdminJsonView.class, UserJsonViews.AuthJsonView.class})
    private Long id;

    @JsonView({UserJsonViews.AdminJsonView.class, UserJsonViews.UserJsonView.class, UserJsonViews.AuthJsonView.class})
    private String name;

    @JsonView({UserJsonViews.AdminJsonView.class, UserJsonViews.AuthJsonView.class})
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
    @JsonView({UserJsonViews.AuthJsonView.class})
    private String password;

    @Column(nullable = false)
    @JsonView({UserJsonViews.AdminJsonView.class, UserJsonViews.AuthJsonView.class})
    private String mail;

    @JsonView({UserJsonViews.AdminJsonView.class,UserJsonViews.AuthJsonView.class})
    @Column(nullable = false)
    private String phone;

    @JsonView({UserJsonViews.AdminJsonView.class})
    @Column(nullable = false)
    private boolean isEnabled;

    @JsonView({UserJsonViews.AdminJsonView.class})
    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_to_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles ;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "comments",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonView({UserJsonViews.AdminJsonView.class, UserJsonViews.UserJsonView.class})
    private List<Comment> comments ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

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
