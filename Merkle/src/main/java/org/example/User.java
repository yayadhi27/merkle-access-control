package org.example;

public class User {
    public String name;
    public String subscriptionDate;
    public String expirationDate;
    public boolean isActive;

    public User(String name, String subscriptionDate, String expirationDate, boolean isActive) {
        this.name = name;
        this.subscriptionDate = subscriptionDate;
        this.expirationDate = expirationDate;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return name + "|" + subscriptionDate;
    }
}
