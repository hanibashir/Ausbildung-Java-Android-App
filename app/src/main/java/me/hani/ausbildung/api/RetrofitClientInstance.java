package me.hani.ausbildung.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.hani.ausbildung.extras.Constants.HOME_API_URL;

public class RetrofitClientInstance {
    private static Retrofit retrofit;

    public static synchronized Retrofit getRetrofitInstance(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(HOME_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
