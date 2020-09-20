package com.example.sen1;

public class Productscart
{

    String productname;
    String productprice;
    String productdiscription;
    String image;
    int quantity;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductdiscription() {
        return productdiscription;
    }

    public void setProductdiscription(String productdiscription)
    {
        this.productdiscription = productdiscription;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Productscart()
    {
    }

    public Productscart(String productname, String productprice, String productdiscription, String image, int quantity)
    {
        this.productname = productname;
        this.productprice = productprice;
        this.productdiscription = productdiscription;
        this.image = image;
        this.quantity = quantity;
    }
}
