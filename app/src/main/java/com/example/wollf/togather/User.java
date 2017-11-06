package com.example.wollf.togather;

import android.media.Image;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by wollf on 17-10-2017.
 */

public class User implements Serializable{
    private String name;
    private String email;
    private String password;
    private String IBAN;
    private String phone;
    private Image profile_picture;
    private String[] allergies;
    private Calendar join_date;
    private String uniqueID;

    public User(String name, String email, String password){
        this.uniqueID = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        // TODO: to be initialized in contructor
        this.IBAN = "NL23RABO34";
        this.phone = "4083204";
        this.join_date = Calendar.getInstance();
    }

    public String getUniqueID() { return uniqueID; }
    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Image getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(Image profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String[] getAllergies() {
        return allergies;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }

    public Calendar getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Calendar join_date) {
        this.join_date = join_date;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
