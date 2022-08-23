package com.techelevator.tenmo.model;

public class UserDirectory {

    private Long id;
    private String username;

    public UserDirectory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDirectory{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
