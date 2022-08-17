package me.hani.ausbildung.models;

import com.google.gson.annotations.SerializedName;

public class CategoryItem {

    @SerializedName("id")
    private int mId;
    @SerializedName("cat_name")
    private String mCatName;
    @SerializedName("img_url")
    private String mThumbnail;
    @SerializedName("auses_count")
    private String mAusesCount;

    public CategoryItem() {
    }

    public int getmId() {
        return mId;
    }

    public String getmCatName() {
        return mCatName;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }
    public String getmAusesCount() {
        return mAusesCount;
    }
}
