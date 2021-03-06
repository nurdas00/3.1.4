package com.example.first_spring_boot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

    public Role(Long id, String name) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*@Override
    public String getAuthority() {
        return name;
    }*/

    @Override
    public String toString(){
        return name;
    }
}
