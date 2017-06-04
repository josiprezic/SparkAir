package com.josip.sparkair;


/**
 * Created by Josip on 28.5.2017..
 */

public class User {
    private int userID;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String image;
    private boolean active;
    private int type; // (-1) = guest, 1 = obicni user, 2 = admin


    public User(int userID, String username, String password, String name, String surname, String image, boolean active, int type) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.image = image;
        this.active = active;
        this.type = type;
    }

    public User() {
        userID = -1;
        username = "Guest";
        password = "Guest";
        name = "Guest";
        surname = " ";
        image = "slika";
        active = false;
        type = -1;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
