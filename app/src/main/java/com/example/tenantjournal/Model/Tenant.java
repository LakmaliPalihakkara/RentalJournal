package com.example.tenantjournal.Model;

public class Tenant {
    String passport;
    String fullName;
    String checkInDate;
    String checkOutDate;
    String gender;
    String profession;
    String phoneNumber;
    String depositPaid;
    Boolean signed;

    public Tenant(String passport, String fullName, String checkInDate, String checkOutDate, String gender, String profession, String phoneNumber,String depositPaid, Boolean signed) {
        this.passport = passport;
        this.fullName = fullName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.gender = gender;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.depositPaid = depositPaid;
        this.signed = signed;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(String depositPaid) {
        this.depositPaid = depositPaid;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }
}
