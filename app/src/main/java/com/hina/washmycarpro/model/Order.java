package com.hina.washmycarpro.model;

public class Order {

    String Date;
    String Name;
    String ServiceOrdered;
    String serviceProvider;
    String Time;
    String totalAmount;
    String email;

    public Order() {
    }

    public String getDate() {
        return Date;

    }

    public void setDate(String Date) {

        this.Date = Date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getServiceOrdered() {
        return ServiceOrdered;
    }

    public void setServiceOrdered(String ServiceOrdered) {
        this.ServiceOrdered = ServiceOrdered;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
