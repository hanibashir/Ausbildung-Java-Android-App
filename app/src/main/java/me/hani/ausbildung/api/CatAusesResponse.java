package me.hani.ausbildung.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.hani.ausbildung.models.AusItem;

public class CatAusesResponse {

    @SerializedName("current_page")
    private String currentPage;

    @SerializedName("data")
    private ArrayList<AusItem> catAusesList;

    public ArrayList<AusItem> getCatAusesList() {
        return catAusesList;
    }

}
