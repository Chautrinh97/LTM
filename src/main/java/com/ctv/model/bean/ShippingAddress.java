package com.ctv.model.bean;

public class ShippingAddress {
    private int customerId;
    private String recipientName;
    private String phoneNumber;
    private String address;

    public ShippingAddress(int customerId, String recipientName, String phoneNumber, String address) {
        this.customerId = customerId;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public ShippingAddress(String recipientName, String phoneNumber, String address) {
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public ShippingAddress(ShippingAddress shippingAddress) {
        this.customerId = shippingAddress.customerId;
        this.recipientName = shippingAddress.recipientName;
        this.phoneNumber = shippingAddress.phoneNumber;
        this.address = shippingAddress.address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
