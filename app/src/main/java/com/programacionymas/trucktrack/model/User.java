package com.programacionymas.trucktrack.model;

/*
"id":7,"name":"Juan","email":"juancagb.17@gmail.com","admin":1,
"created_at":"2017-07-14 00:49:16","updated_at":"2017-07-14 00:49:16",
"dni":null,"phone":null,"address":null,"birth_date":null,"license":null,"deleted_at":null,"truck_id":null
*/

public class User {

    private int id;
    private String name;
    private String email;
    private String dni;
    private String phone;
    private String address;
    private String license;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
