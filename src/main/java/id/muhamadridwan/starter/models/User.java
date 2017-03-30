/*
 * Copyright (c) 2017,  Muhamadridwan.id
 * All rights reserved.
 */
package id.muhamadridwan.starter.models;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author mridwan
 */
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 4, message = "USERNAME must be at least 4 chars ! ! ! ")
    private String username;

    @NotNull
    @NotEmpty(message = "Password should be entered")
    @Size(min = 6, message = "Password should be at least 6 chars ! ! !")
    private String password;

    @NotNull
    @NotEmpty(message = "First name should be entered")
    private String firstname;

    @NotNull
    @NotEmpty(message = "Last name should be entered")
    private String lastname;

    @NotNull
    @NotEmpty(message = "Email should be entered")
    @Email(message = "Invalid email address ! ! !")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessedDate;

    private boolean enabled;

    private boolean tokenExpired;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany
    private Set<Role> role;

    //Constructor
    public User() {
    }

    public User(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.tokenExpired = false;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastAccessedDate() {
        return lastAccessedDate;
    }

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public Set<Role> getRoles() {
        return role;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(getRoles());
        authorities.addAll(getPermissions());
        return authorities;
    }

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
        this.enabled = true;
        return this.enabled;
    }

    //Other
    @Transient
    public Set<Permission> getPermissions() {
        Set<Permission> permissions = new HashSet<>();
        role.forEach((role) -> {
            permissions.addAll(role.getPermission());
        });

        return permissions;
    }

    @PrePersist
    protected void onCreate() {
        setCreationDate(new Date());
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", creationDate=" + creationDate + ", lastAccessedDate=" + lastAccessedDate + ", enabled=" + enabled + ", tokenExpired=" + tokenExpired + ", roles=" + role + '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLastAccessedDate(Date lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public void setRoles(Set<Role> roles) {
        this.role = roles;
    }

}
