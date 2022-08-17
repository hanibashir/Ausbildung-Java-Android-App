package me.hani.ausbildung.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.hani.ausbildung.models.PageItem;

public class PagesResponse {

    @SerializedName("current_page")
    private String currentPage;

    @SerializedName("data")
    private ArrayList<PageItem> pagesList;

    public ArrayList<PageItem> getpagesList() {
        return pagesList;
    }

}
