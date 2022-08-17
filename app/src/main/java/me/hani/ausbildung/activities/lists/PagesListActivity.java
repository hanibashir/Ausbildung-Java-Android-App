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
import me.hani.ausbildung.adapters.PagesAdapter;
import me.hani.ausbildung.api.PagesResponse;
import me.hani.ausbildung.api.RetrofitClientInstance;
import me.hani.ausbildung.extras.CheckConnection;
import me.hani.ausbildung.extras.EndlessRecyclerViewScrollListener;
import me.hani.ausbildung.interfaces.RetrofitInterface;
import me.hani.ausbildung.models.PageItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagesListActivity extends AppCompatActivity {

    private SwipeRefreshLayout pageRefreshLayout;
    private RecyclerView mRecyclerView;
    private PagesAdapter mPagesAdapter;
    private ArrayList<PageItem> mPageList;
    private CheckConnection mCheckConnection;
    private RetrofitInterface api;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;

    private int mPagination =1;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages_list);

        initViews();

        if (mCheckConnection.isNetworkConnected()) {

            setValues();
            onLayoutRefreshed();

        } else {//No Connection

            mCheckConnection.showNoConnectionAlert("لايوجد اتصال بالانترنت",
                    "التطبيق يحتاج للاتصال بشبكة الانترنت لإظهار قائمة تصنيفات المهن.", "المحاولة ثانية", "إلغاء");

        }


    }

    private void initViews(){
        //toolbar
        toolbar = findViewById(R.id.pages_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("معلومات متنوعة عن التدريب المهني");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // refresh layout
        pageRefreshLayout = findViewById(R.id.page_refresh_layout);

        //recyclerView layout manager
        linearLayoutManager = new LinearLayoutManager(PagesListActivity.this);

        mRecyclerView = findViewById(R.id.rv_pages);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mPageList = new ArrayList<>();


        mCheckConnection = new CheckConnection(this);

        progressBar = (ProgressBar) findViewById(R.id.pages_list_pb);
    }

    private void setValues(){
        getPages(mPagination);

        mPagesAdapter = new PagesAdapter(PagesListActivity.this, mPageList);
        mRecyclerView.setAdapter(mPagesAdapter);

        onScrollLoadMore();
    }

    private void getPages(int pagination) {

        api = RetrofitClientInstance.getRetrofitInstance().create(RetrofitInterface.class);

        Call<PagesResponse> catsCall = api.getPages(pagination);
        catsCall.enqueue(new Callback<PagesResponse>() {
            @Override
            public void onResponse(Call<PagesResponse> call, Response<PagesResponse> response) {

                if (response.isSuccessful()) {

                    mPageList.addAll(response.body().getpagesList());

                    // notifying adapter to add the new data to the list
                    mPagesAdapter.notifyItemRangeInserted(mPagesAdapter.getItemCount(), mPageList.size() - 1);

                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(PagesListActivity.this, getResources().getString(R.string.retrofit_success), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PagesListActivity.this, getResources().getString(R.string.retrofit_failed), Toast.LENGTH_SHORT).show();
                    Log.d("img", "" + "Error" + response.code());
                }

            }


            @Override
            public void onFailure(Call<PagesResponse> call, Throwable t) {

                Log.d("img", "" + "Error" + t.getMessage());
            }
        });
    }


    private void onScrollLoadMore() {
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPagination++;
                getPages(mPagination);
            }
        });
    }

    private void onLayoutRefreshed(){

        pageRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        pageRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        mPageList.clear();
                        mPagination =1;
                        setValues();
                        pageRefreshLayout.setRefreshing(false);
                    }
                }, 3000);

            }
        });


    }



}