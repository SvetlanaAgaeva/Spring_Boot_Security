package ru.svetlanaagaeva.pp_3_1_2_spring_boot_security.model;

import jakarta.persistence.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users_security")

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    ////  мой код который работад
    //private Set<Role> roles ;

        //гпт сказал
    private Set<Role> roles = new HashSet<>();

    //непонятно надо или нет
//    public String getRolesString() {
//        return roles.stream()
//                .map(Role::getName)
//                .collect(Collectors.joining(" "));
//    }
//    private String rolesString;

    // Геттер и сеттер для rolesString
//    public String getRolesString() {
//        return rolesString;
//    }

//    public void setRolesString(String rolesString) {
//        this.rolesString = rolesString;
//    }

    // Геттер и сеттер для roles
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    public String getRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return String.join(" ", AuthorityUtils.authorityListToSet(getRoles()));
    }
    public User() {
    }

    public User(String name, String surname, String username, String password, Set<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


//    public String getRolesAsString() {
//        return roles.stream()
//                .map(Role::getName)
//                .collect(Collectors.joining(" "));
//    }
//    public String getRole() {
//        return roles.stream()
//                .map(Role::getName)
//                .collect(Collectors.joining(" "));
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


//public String getRole() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return String.join(" ", AuthorityUtils.authorityListToSet(getRoles()));
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return roles;
//    }

//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return userName;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }

//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
