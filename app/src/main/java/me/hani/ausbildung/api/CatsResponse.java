package me.hani.ausbildung.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.hani.ausbildung.models.CategoryItem;

public class CatsResponse {

    @SerializedName("current_page")
    private String currentPage;

    @SerializedName("data")
    private ArrayList<CategoryItem> catsList;

    public ArrayList<CategoryItem> getCatsList() {
        return catsList;
    }
}
