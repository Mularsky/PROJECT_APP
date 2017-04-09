package com.example.drs.tb17app;

public class User {

    public String user_ID; //requred yes
    public String facebook_ID; // required no if google_ID yes
    public String email;
    public String google_ID; //required no if facebook_ID yes
    public boolean if_admin = false; //required yes
    public String username; //required yes
    //profile_pic [jpg, png, gif] //required no
    public String about_me; //required no
    public int age; //required no
    public String my_city; //required no
    public String account_type; //standard/premium/banned //required yes
    //gps //required yes
    public String public_key; //reqiured yes
    public String private_key; //required yes
    //my_history //List of Shout Objects //reqiured yes
    //my_watched //List of Shout Objects //reqiured yes
    //my_messages //List of Messages Objects //required yes
    //my_friends //List of User Objects //required yes
    //my_banned //List of User Objects //required yes

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getFacebook_ID() {
        return facebook_ID;
    }

    public void setFacebook_ID(String facebook_ID) {
        this.facebook_ID = facebook_ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogle_ID() {
        return google_ID;
    }

    public void setGoogle_ID(String google_ID) {
        this.google_ID = google_ID;
    }

    public boolean isIf_admin() {
        return if_admin;
    }

    public void setIf_admin(boolean if_admin) {
        this.if_admin = if_admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMy_city() {
        return my_city;
    }

    public void setMy_city(String my_city) {
        this.my_city = my_city;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }
}
