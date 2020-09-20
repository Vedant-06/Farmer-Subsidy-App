package com.example.sen1;

public class Products
{

    String productname;
    String productprice;
    String productdiscription;
    String image;

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getProductname()
    {
        return productname;
    }

    public void setProductname(String productname)
    {
        this.productname = productname;
    }

    public String getProductprice()
    {
        return productprice;
    }

    public void setProductprice(String productprice)
    {
        this.productprice = productprice;
    }

    public String getProductdiscription()
    {
        return productdiscription;
    }

    public void setProductdiscription(String productdiscription)
    {
        this.productdiscription = productdiscription;
    }

    public Products()
    {
    }

    public Products(String productname, String productprice, String productdiscription,String image)
    {
        this.productname = productname;
        this.productprice = productprice;
        this.productdiscription = productdiscription;
        this.image=image;
    }
}
