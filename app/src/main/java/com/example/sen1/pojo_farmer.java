package com.example.sen1;

public class pojo_farmer
{
    String  name;
    String username;
    String password;
    String birthdate;
    String city;

    String state;
    double land;
    double income;
    String contact;
    String img;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }



    public double getLand() {
        return land;
    }

    public void setLand(double land) {
        this.land = land;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public pojo_farmer()
    {


    }

    public pojo_farmer(String n,String u,String p,String b,String c,String s, double i,double l,String con,String img)
    {
        this.name=n;
        this.username=u;
        this.password=p;
        this.birthdate=b;
        this.city=c;
        this.state=s;
        this.income=i;
        this.land=l;
        this.contact=con;
        this.img=img;
    }

}