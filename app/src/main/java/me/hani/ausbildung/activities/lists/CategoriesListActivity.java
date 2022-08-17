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

import me.hani.ausbildung.R;
import me.hani.ausbildung.adapters.CategoriesAdapter;
import me.hani.ausbildung.api.CatsResponse;
import me.hani.ausbildung.api.RetrofitClientInstance;
import me.hani.ausbildung.extras.EndlessRecyclerViewScrollListener;
import me.hani.ausbildung.interfaces.RetrofitInterface;
import me.hani.ausbildung.models.CategoryItem;
import me.hani.ausbildung.extras.CheckConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesListActivity extends AppCompatActivity {

    private SwipeRefreshLayout catRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerview;
    private CategoriesAdapter mCategoriesAdapter;
    private ArrayList<CategoryItem> mCatsList;
    private RetrofitInterface api;
    private CheckConnection mCheckConnection;
    private int mPagination =1;
    private Toolbar toolbar;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list);

        initViews();

        if (mCheckConnection.isNetworkConnected()) {


            setValues();

            onLayoutRefreshed();

        } else {//No Connection

            mCheckConnection.showNoConnectionAlert("لايوجد اتصال بالانترنت",
                    "التطبيق يحتاج للاتصال بشبكة الانترنت لإظهار قائمة تصنيفات المهن.", "المحاولة ثانية", "إلغاء");

        }
    }

    private void initViews() {

        catRefreshLayout = findViewById(R.id.cats_refresh_layout);

        toolbar = findViewById(R.id.cat_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("تصنيفات المهن");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerview = findViewById(R.id.rv_cat);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(linearLayoutManager);

        mCatsList = new ArrayList<>();

        mCheckConnection = new CheckConnection(this);

        progressBar = (ProgressBar) findViewById(R.id.cats_list_pb);
    }

    private void setValues() {


        getCats(mPagination);

        mCategoriesAdapter = new CategoriesAdapter(CategoriesListActivity.this, mCatsList);
        mRecyclerview.setAdapter(mCategoriesAdapter);

        onScrollLoadMore();
    }




    private void getCats(int pagination) {
        api = RetrofitClientInstance.getRetrofitInstance().create(RetrofitInterface.class);

        Call<CatsResponse> catsCall = api.getCats(pagination);
        catsCall.enqueue(new Callback<CatsResponse>() {
            @Override
            public void onResponse(Call<CatsResponse> call, Response<CatsResponse> response) {

                if (response.isSuccessful()) {

                    mCatsList.addAll(response.body().getCatsList());

                    // notifying adapter to add the new data to the list
                    mCategoriesAdapter.notifyItemRangeInserted(mCategoriesAdapter.getItemCount(), mCatsList.size() - 1);

                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(CategoriesListActivity.this, getResources().getString(R.string.retrofit_success), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CategoriesListActivity.this, getResources().getString(R.string.retrofit_failed), Toast.LENGTH_SHORT).show();
                    Log.d("img", "" + "Error" + response.code());
                }

            }


            @Override
            public void onFailure(Call<CatsResponse> call, Throwable t) {

                Log.d("img", "" + "Error" + t.getMessage());
            }
        });
    }

    private void onScrollLoadMore() {
        mRecyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPagination++;

                getCats(mPagination);

            }
        });
    }

    private void onLayoutRefreshed(){

        catRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        catRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mCatsList.clear();
                        mPagination =1;
                        setValues();
                        catRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });

    }
}