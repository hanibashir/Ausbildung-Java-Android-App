package me.hani.ausbildung.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import me.hani.ausbildung.models.AusItem;

public class BestSalaryAusesResponse {

    @SerializedName("current_page")
    private String currentPage;

    @SerializedName("data")
    private ArrayList<AusItem> bestSalaryAusesusesList;

    public ArrayList<AusItem> getBestSalaryAusesList() {
        return bestSalaryAusesusesList;
    }

}
