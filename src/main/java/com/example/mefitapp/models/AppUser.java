package com.example.mefitapp.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_contributor;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_admin;

    @OneToOne(mappedBy = "app_user")
    private Profile profile;

    @JsonGetter("profile")
    public String profileGetter() {
        if(profile != null) {
            return "/api/v1/profiles/" + profile.getId();
        }
        return null;
    }

    public AppUser() {
    }

    // Used in DatabaseSeeder
    public AppUser(String email, String password, Boolean is_contributor, Boolean is_admin) {
        this.email = email;
        this.password = password;
        this.is_contributor = is_contributor;
        this.is_admin = is_admin;
    }

    public long getId() {
        return id;
    }

    public void setId(long user_id) {
        this.id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_contributor() {
        return is_contributor;
    }

    public void setIs_contributor(Boolean is_contributor) {
        this.is_contributor = is_contributor;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}