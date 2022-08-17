package me.hani.ausbildung.activities.lists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import me.hani.ausbildung.api.AusesResponse;
import me.hani.ausbildung.api.BestAusesResponse;
import me.hani.ausbildung.api.BestSalaryAusesResponse;
import me.hani.ausbildung.api.CatAusesResponse;
import me.hani.ausbildung.R;
import me.hani.ausbildung.adapters.AusesAdapter;
import me.hani.ausbildung.extras.EndlessRecyclerViewScrollListener;
import me.hani.ausbildung.api.RetrofitClientInstance;
import me.hani.ausbildung.interfaces.RetrofitInterface;
import me.hani.ausbildung.models.AusItem;
import me.hani.ausbildung.extras.CheckConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AusesListActivity extends AppCompatActivity {

    private SwipeRefreshLayout ausesRefreshedLayout;
    private Toolbar toolbar;
    private CheckConnection mCheckConnection;
    private RecyclerView mRecyclerview;
    private int mPagination = 1;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<AusItem> mAusesList;
    private AusesAdapter mAdapter;
    private RetrofitInterface api;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auses_list);

        intViews();


        if (mCheckConnection.isNetworkConnected()) { // if there is internet connection

            setValues();

            onLayoutRefreshed();

        } else {//No internet connection

            mCheckConnection.showNoConnectionAlert("لايوجد اتصال بالانترنت",
                    "التطبيق يحتاج للاتصال بشبكة الانترنت لإظهار قائمة المهن.", "المحاولة ثانية", "إلغاء");

        }
    }


    private void setValues(){

        String ausType = getIntent().getStringExtra("auses_type");

        if (ausType != null) {
            switch (ausType) {
                case "best_aus":
                    //change toolbar title
                    getSupportActionBar().setTitle(getResources().getString(R.string.best_auses));

                    getAuses("best_aus", mPagination);
                    mAdapter = new AusesAdapter(AusesListActivity.this, mAusesList);
                    mRecyclerview.setAdapter(mAdapter);

                    //loadMore();
                    onScrollLoadMore("best_aus");
                    break;

                case "best_salary":
                    //change toolbar title
                    getSupportActionBar().setTitle(getResources().getString(R.string.best_salary));

                    getAuses("best_salary", mPagination);
                    mAdapter = new AusesAdapter(AusesListActivity.this, mAusesList);
                    mRecyclerview.setAdapter(mAdapter);

                    //loadMore();
                    onScrollLoadMore("best_salary");
                    break;
                case "cat_auses":
                    //change toolbar title
                    String catName = getIntent().getStringExtra("cat_name");
                    getSupportActionBar().setTitle(getResources().getString(R.string.cat_auses_list) + ": " + catName);
                    getAuses("cat_auses", mPagination);
                    mAdapter = new AusesAdapter(AusesListActivity.this, mAusesList);
                    mRecyclerview.setAdapter(mAdapter);

                    //loadMore();
                    onScrollLoadMore("cat_auses");
                    break;
            }
        } else {

            //change toolbar title
            getSupportActionBar().setTitle(getResources().getString(R.string.all_auses));

            //then get all auses;
            getAuses("auses", mPagination);

            mAdapter = new AusesAdapter(AusesListActivity.this, mAusesList);
            mRecyclerview.setAdapter(mAdapter);

            //loadMore();
            onScrollLoadMore("auses");

        }
    }

    private void intViews() {

        toolbar = findViewById(R.id.all_aus_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ausesRefreshedLayout = findViewById(R.id.auses_refresh_layout);

        linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerview = findViewById(R.id.rv_all_aus);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        mRecyclerview.setHasFixedSize(true);

        api = RetrofitClientInstance.getRetrofitInstance().create(RetrofitInterface.class);

        mAusesList = new ArrayList<>();

        mCheckConnection = new CheckConnection(this);

        progressBar = (ProgressBar) findViewById(R.id.auses_list_pb);
    }



    private void getAuses(String type, int pagination) {

        switch (type) {
            case "auses":
                Call<AusesResponse> ausesCall = api.getAusesList(pagination);
                ausesCall.enqueue(new Callback<AusesResponse>() {
                    @Override
                    public void onResponse(Call<AusesResponse> call, Response<AusesResponse> response) {

                        if (response.isSuccessful()) {


                            //Log.d("img",""+ response.body().get(0).getmImageUrl());

                            // fill auses list with the response
                            mAusesList.addAll(response.body().getAusesList());
                            // notifying adapter to add the new data to the list
                            mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), mAusesList.size() - 1);

                            progressBar.setVisibility(View.GONE);
                            //Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_success), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_failed), Toast.LENGTH_SHORT)
                                    .show();
                            Log.d("Error", "" + "Error" + response.code());

                        }

                    }

                    @Override
                    public void onFailure(Call<AusesResponse> call, Throwable t) {


                        Toast.makeText(AusesListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("Error", "" + "Error" + t.getMessage());
                    }
                });
                break;
            case "best_aus":
                Call<BestAusesResponse> bestAusesCall = api.getBestAusesList(pagination);
                bestAusesCall.enqueue(new Callback<BestAusesResponse>() {
                    @Override
                    public void onResponse(Call<BestAusesResponse> call, Response<BestAusesResponse> response) {

                        if (response.isSuccessful()) {

                            //Log.d("img",""+ response.body().get(0).getmImageUrl());

                            // fill auses list with the response
                            mAusesList.addAll(response.body().getBestAusesList());
                            // notifying adapter to add the new data to the list
                            mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), mAusesList.size() - 1);

                            progressBar.setVisibility(View.GONE);
                            //Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_success), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_failed), Toast.LENGTH_SHORT)
                                    .show();
                            Log.d("Error", "" + "Error" + response.code());

                        }

                    }

                    @Override
                    public void onFailure(Call<BestAusesResponse> call, Throwable t) {

                        Toast.makeText(AusesListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("Error", "" + "Error" + t.getMessage());
                    }
                });
                break;
            case "best_salary":
                Call<BestSalaryAusesResponse> bestSalaryAusesCall = api.getBestSalaryAusesList(pagination);
                bestSalaryAusesCall.enqueue(new Callback<BestSalaryAusesResponse>() {
                    @Override
                    public void onResponse(Call<BestSalaryAusesResponse> call, Response<BestSalaryAusesResponse> response) {

                        if (response.isSuccessful()) {

                            //Log.d("img",""+ response.body().get(0).getmImageUrl());

                            // fill auses list with the response
                            mAusesList.addAll(response.body().getBestSalaryAusesList());
                            // notifying adapter to add the new data to the list
                            mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), mAusesList.size() - 1);

                            progressBar.setVisibility(View.GONE);
                            //Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_success), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_failed), Toast.LENGTH_SHORT)
                                    .show();
                            Log.d("Error", "" + "Error" + response.code());

                        }

                    }

                    @Override
                    public void onFailure(Call<BestSalaryAusesResponse> call, Throwable t) {

                        Toast.makeText(AusesListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("Error", "" + "Error" + t.getMessage());
                    }
                });
                break;
            case "cat_auses":
                int clickedCatId = getIntent().getIntExtra("cat_item_id", 0);

                Call<CatAusesResponse> catAuses = api.getCatAuses(clickedCatId, pagination);
                catAuses.enqueue(new Callback<CatAusesResponse>() {
                    @Override
                    public void onResponse(Call<CatAusesResponse> call, Response<CatAusesResponse> response) {

                        if (response.isSuccessful()) {

                            //Log.d("img",""+ response.body().get(0).getmImageUrl());

                            // fill auses list with the response
                            mAusesList.addAll(response.body().getCatAusesList());
                            // notifying adapter to add the new data to the list
                            mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), mAusesList.size() - 1);

                            progressBar.setVisibility(View.GONE);
                            //Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_success), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(AusesListActivity.this, getResources().getString(R.string.retrofit_failed), Toast.LENGTH_SHORT)
                                    .show();
                            Log.d("Error", "" + "Error" + response.code());

                        }

                    }

                    @Override
                    public void onFailure(Call<CatAusesResponse> call, Throwable t) {

                        Toast.makeText(AusesListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("Error", "" + "Error" + t.getMessage());
                    }
                });
                break;
        }

    }

    private void onScrollLoadMore(String type) {
        mRecyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPagination++;
                getAuses(type, mPagination);
            }
        });
    }

    private void onLayoutRefreshed(){

        ausesRefreshedLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                ausesRefreshedLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mAusesList.clear();
                        mPagination =1;
                        setValues();
                        ausesRefreshedLayout.setRefreshing(false);

                    }
                }, 5000);

            }
        });


    }

}
