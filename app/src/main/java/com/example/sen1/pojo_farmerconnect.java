package com.example.sen1;

public class pojo_farmerconnect
{
    private String farmername;
    private String contact;
    private String expertise;

    public pojo_farmerconnect()
    {

    }
    public pojo_farmerconnect(String farmername, String contact, String expertise)
    {
        this.farmername = farmername;
        this.contact = contact;
        this.expertise = expertise;
    }

    public String getFarmername() {
        return farmername;
    }

    public void setFarmername(String farmername) {
        this.farmername = farmername;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}

