package com.example.wollf.togather;

import android.media.Image;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    private String tikkie_user_token;
    private String tikkie_iban_token;

    public String getTikkie_user_token() {
        return tikkie_user_token;
    }

    public void setTikkie_user_token(String tikkie_user_token) {
        this.tikkie_user_token = tikkie_user_token;
    }

    public String getTikkie_iban_token() {
        return tikkie_iban_token;
    }

    public void setTikkie_iban_token(String tikkie_iban_token) {
        this.tikkie_iban_token = tikkie_iban_token;
    }

    private Map<String, Group> groups;

    public User(String name, String email, String password){
        this.uniqueID = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.IBAN = "NL23RABO34";
        this.phone = "4083204";
        this.tikkie_user_token = "2099e755-ab32-4e80-b6fc-b870155b12de";
        this.tikkie_iban_token = "2e01490c-972b-4b21-9233-cc87b91ba044";
        this.join_date = Calendar.getInstance();
        groups = new HashMap<>();
    }

    public User(String name, String email, String password,String iban, String phone, String tikkie_user, String tikkie_iban){
        this.uniqueID = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.allergies = new String[]{"peanuts", "milk"};
        this.IBAN = iban;
        this.phone = phone;
        this.join_date = Calendar.getInstance();
        this.tikkie_user_token = tikkie_user;
        this.tikkie_iban_token = tikkie_iban;
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
    public Group getGroupById(String groupID){
        return groups.get(groupID);
    }
}
