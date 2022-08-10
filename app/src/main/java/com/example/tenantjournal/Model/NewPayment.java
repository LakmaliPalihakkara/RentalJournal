package com.example.tenantjournal.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class NewPayment implements Parcelable {
    String tenantName;
    String propertyName;
    String paymentDate;
    String rentalFeePaid;
    String damagePayment;

    public NewPayment(String tenantName, String propertyName, String paymentDate, String rentalFeePaid, String damagePayment) {
        this.tenantName = tenantName;
        this.propertyName = propertyName;
        this.paymentDate = paymentDate;
        this.rentalFeePaid = rentalFeePaid;
        this.damagePayment = damagePayment;
    }

    protected NewPayment(Parcel in) {
        tenantName = in.readString();
        propertyName = in.readString();
        paymentDate = in.readString();
        rentalFeePaid = in.readString();
        damagePayment = in.readString();
    }

    public static final Creator<NewPayment> CREATOR = new Creator<NewPayment>() {
        @Override
        public NewPayment createFromParcel(Parcel in) {
            return new NewPayment(in);
        }

        @Override
        public NewPayment[] newArray(int size) {
            return new NewPayment[size];
        }
    };

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRentalFeePaid() {
        return rentalFeePaid;
    }

    public void setRentalFeePaid(String rentalFeePaid) {
        this.rentalFeePaid = rentalFeePaid;
    }

    public String getDamagePayment() {
        return damagePayment;
    }

    public void setDamagePayment(String damagePayment) {
        this.damagePayment = damagePayment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tenantName);
        parcel.writeString(propertyName);
        parcel.writeString(paymentDate);
        parcel.writeString(rentalFeePaid);
        parcel.writeString(damagePayment);
    }
}
