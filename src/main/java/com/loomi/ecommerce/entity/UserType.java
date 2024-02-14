package com.loomi.ecommerce.entity;

public enum UserType {
    ADMIN("admin"),
    PAYMENT("payment"),
    CUSTOMER("customer");

    private String type;

    UserType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
