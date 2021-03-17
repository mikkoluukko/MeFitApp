package com.example.mefitapp.models;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

// Had to name the class Users with S, because User
// is a reserved word PostgreSQL
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_contributor;

    @Column(columnDefinition = "boolean default false")
    private Boolean is_admin;

    public Users() {
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
}