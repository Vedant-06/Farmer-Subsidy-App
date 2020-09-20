package com.example.sen1;

public class pojo_mysubsidy
{
    private String subsidyname;
    private String status;
    private String amount;

    public pojo_mysubsidy(){

    }

    public String getSubsidyname() {
        return subsidyname;
    }

    public void setSubsidyname(String subsidyname) {
        this.subsidyname = subsidyname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public pojo_mysubsidy(String subsidyname, String status, String amount)
    {
        this.subsidyname = subsidyname;
        this.status = status;
        this.amount = amount;
    }
}
