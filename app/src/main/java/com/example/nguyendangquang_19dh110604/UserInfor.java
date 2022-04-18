package com.example.nguyendangquang_19dh110604;

import java.io.Serializable;

public class UserInfor implements Serializable  {
    String address;
    String email;
    String firstname;
    String lastname;
    String mobile;
    String password;

    public UserInfor(String address, String email, String firstname, String lastname, String mobile, String password) {
        this.address = address;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobile = mobile;
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mobile=" + mobile +
                ", password='" + password + '\'' +
                '}';
    }

    public UserInfor() {
    }
}

