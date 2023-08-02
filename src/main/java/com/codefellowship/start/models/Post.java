package com.codefellowship.start.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long Id;

    String body;
    LocalDateTime createdAt;

    @ManyToOne
    ApplicationUser applicationUser;

    protected Post(){};

    public Post(String body, ApplicationUser applicationUser) {

        this.body = body;
        this.createdAt = LocalDateTime.now();
        this.applicationUser = applicationUser;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public Long getId() {
        return Id;
    }
}
