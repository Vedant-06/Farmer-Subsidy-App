package com.example.sen1;

public class pojo_subsidy
{
    private String subsidyname;
    private String status;
    private String amount;
    private String documents;

    public pojo_subsidy()
    {

    }

    public String getSubsidyname()
    {
        return subsidyname;
    }

    public void setSubsidyname(String subsidyname)
    {
        this.subsidyname = subsidyname;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getDocuments()
    {
        return documents;
    }

    public void setDocuments(String documents)
    {
        this.documents = documents;
    }

    public pojo_subsidy(String subsidyname, String status, String amount)
    {
        this.subsidyname = subsidyname;
        this.status = status;
        this.amount = amount;
    }
}