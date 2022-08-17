package me.hani.ausbildung.interfaces;

import java.util.ArrayList;

import me.hani.ausbildung.api.AusesResponse;
import me.hani.ausbildung.api.BestAusesResponse;
import me.hani.ausbildung.api.BestSalaryAusesResponse;
import me.hani.ausbildung.api.CatAusesResponse;
import me.hani.ausbildung.api.CatsResponse;
import me.hani.ausbildung.api.PagesResponse;
import me.hani.ausbildung.models.AboutItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static me.hani.ausbildung.extras.Constants.*;

public interface RetrofitInterface {

    @GET(AUSES)
    Call<AusesResponse> getAusesList(@Query("page") int page);

    @GET(BEST_AUS)
    Call<BestAusesResponse> getBestAusesList(@Query("page") int page);

    @GET(BEST_SALARY)
    Call<BestSalaryAusesResponse> getBestSalaryAusesList(@Query("page") int page);

    @GET(CATS + "/{cat_id}")
    Call<CatAusesResponse> getCatAuses(@Path("cat_id") int cat_id, @Query("page") int page);

    @GET(CATS)
    Call<CatsResponse> getCats(@Query("page") int page);

    @GET(PAGES)
    Call<PagesResponse> getPages(@Query("page") int page);

    @GET(ABOUT)
    Call<ArrayList<AboutItem>> getAbout();
}
