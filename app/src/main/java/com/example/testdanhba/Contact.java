package com.example.testdanhba;


import androidx.annotation.NonNull;

import java.util.Comparator;

public class Contact implements Comparable<Contact> {
    private String name;
    private String phone;
    private String photo;
    public Contact(){

    }

    public Contact(String name, String phone, String photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
    }

    @Override
    public int compareTo(@NonNull Contact other) {
        return this.name.compareTo(other.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }



}
