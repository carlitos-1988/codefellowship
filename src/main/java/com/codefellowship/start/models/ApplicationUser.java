package com.codefellowship.start.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String bio;

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Post> posts;

    protected ApplicationUser(){};

    public ApplicationUser(String userName, String password, String firstName, String lastName, Date birthDate, String bio) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.bio = bio;
        this.posts = new ArrayList<>();
    }

    //Many to Many Impl
    @ManyToMany
    @JoinTable(
            name= "followers_to_followers",
            joinColumns = {@JoinColumn(name = "appUserFollowing")},
            inverseJoinColumns = {@JoinColumn(name="followedUser")}
    )
    Set<ApplicationUser>usersIFollow= new HashSet<>();

    @ManyToMany(mappedBy = "usersIFollow")
    Set<ApplicationUser> userWhoFollowMe = new HashSet<>();

    public Set<ApplicationUser> getUsersIFollow() {
        return usersIFollow;
    }

    public void setUsersIFollow(Set<ApplicationUser> usersIFollow) {
        this.usersIFollow = usersIFollow;
    }

    public Set<ApplicationUser> getUserWhoFollowMe() {
        return userWhoFollowMe;
    }

    public void setUserWhoFollowMe(Set<ApplicationUser> userWhoFollowMe) {
        this.userWhoFollowMe = userWhoFollowMe;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return true;
    }

    public Long getId() {
        return Id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBio() {
        return bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPost(Post post) {
        this.posts.add(post);
    }
}
