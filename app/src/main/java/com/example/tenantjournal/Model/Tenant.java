package com.example.tenantjournal.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tenant implements Parcelable {
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

    protected Tenant(Parcel in) {
        passport = in.readString();
        fullName = in.readString();
        checkInDate = in.readString();
        checkOutDate = in.readString();
        gender = in.readString();
        profession = in.readString();
        phoneNumber = in.readString();
        depositPaid = in.readString();
        byte tmpSigned = in.readByte();
        signed = tmpSigned == 0 ? null : tmpSigned == 1;
    }

    public static final Creator<Tenant> CREATOR = new Creator<Tenant>() {
        @Override
        public Tenant createFromParcel(Parcel in) {
            return new Tenant(in);
        }

        @Override
        public Tenant[] newArray(int size) {
            return new Tenant[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(passport);
        parcel.writeString(fullName);
        parcel.writeString(checkInDate);
        parcel.writeString(checkOutDate);
        parcel.writeString(gender);
        parcel.writeString(profession);
        parcel.writeString(phoneNumber);
        parcel.writeString(depositPaid);
        parcel.writeByte((byte) (signed == null ? 0 : signed ? 1 : 2));
    }
}
