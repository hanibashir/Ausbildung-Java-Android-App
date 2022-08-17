package me.hani.ausbildung.models;

import com.google.gson.annotations.SerializedName;

import static me.hani.ausbildung.extras.Constants.IMAGES_BASE_URL;

public class AusItem {

    @SerializedName("title")
    private String mTitle;
    @SerializedName("ar_title")
    private String mArTitle;
    @SerializedName("certificate")
    private String mCertificate;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("img_url")
    private String mImageUrl;

    @SerializedName("cat_id")
    private String mCatId;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("salary1")
    private String mSalary1;
    @SerializedName("salary2")
    private String mSalary2;
    @SerializedName("salary3")
    private String mSalary3;
    @SerializedName("salary4")
    private String mSalary4;

    @SerializedName("best_aus")
    private String mBestAus;
    @SerializedName("best_salary")
    private String mBestSalary;
    @SerializedName("aus_link")
    private String mLink;



    public String getmTitle() {
        return mTitle;
    }

    public String getmArTitle() {
        return mArTitle;
    }

    public String getmCertificate() {
        return mCertificate;
    }

    public String getmDuration() {
        return mDuration;
    }



    public String getmImageUrl() {
        return IMAGES_BASE_URL +"auses/" + mImageUrl;
    }

    public String getmCatId() {
        return mCatId;
    }


    public String getmSalary1() {
        return mSalary1;
    }

    public String getmSalary2() {
        return mSalary2;
    }

    public String getmSalary3() {
        return mSalary3;
    }

    public String getmSalary4() {
        return mSalary4;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmBestAus() {
        return mBestAus;
    }
    public String getmBestSalary() {
        return mBestSalary;
    }


    public String getmLink() {
        return mLink;
    }
}
