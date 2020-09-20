package com.example.sen1;


public class pojo_allsubsidy
{

    private String mSubsidyName;
    private float mCriteria;
    private String mMaxValue;
    private String mState;
    private String mDetails;
    private String mDocuments;

    public pojo_allsubsidy(String mSubsidyName, float mCriteria, String mMaxValue, String mState, String mDocuments, String mDetails)
    {
        this.mSubsidyName = mSubsidyName;
        this.mCriteria = mCriteria;
        this.mMaxValue = mMaxValue;
        this.mState = mState;
    }

    public String getmDetails() {
        return mDetails;
    }

    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public String getmDocuments() {
        return mDocuments;
    }

    public void setmDocuments(String mDocuments) {
        this.mDocuments = mDocuments;
    }

    public pojo_allsubsidy(){


    }

    public String getmSubsidyName() {
        return mSubsidyName;
    }

    public float getmCriteria() {
        return mCriteria;
    }

    public String getmMaxValue() {
        return mMaxValue;
    }

    public String getmState() {
        return mState;
    }

    public void setmSubsidyName(String mSubsidyName) {
        this.mSubsidyName = mSubsidyName;
    }

    public void setmCriteria(float mCriteria) {
        this.mCriteria = mCriteria;
    }

    public void setmMaxValue(String mMaxValue) {
        this.mMaxValue = mMaxValue;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }
}

