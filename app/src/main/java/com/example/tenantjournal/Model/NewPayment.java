package com.example.tenantjournal.Model;

public class NewPayment {
    String tenantName;
    String propertyName;
    String paymentDate;
    String profession;
    String rentalFeePaid;
    String damagePayment;

    public NewPayment(String tenantName, String propertyName, String paymentDate, String profession, String rentalFeePaid, String damagePayment) {
        this.tenantName = tenantName;
        this.propertyName = propertyName;
        this.paymentDate = paymentDate;
        this.profession = profession;
        this.rentalFeePaid = rentalFeePaid;
        this.damagePayment = damagePayment;
    }

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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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
}
