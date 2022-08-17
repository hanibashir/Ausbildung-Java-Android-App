package me.hani.ausbildung.activities.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import me.hani.ausbildung.R;
import me.hani.ausbildung.extras.Ads;

public class ShowPageActivity extends AppCompatActivity {
    private Toolbar pageToolbar;
    private ImageView pageImg;
    private WebView pageArticleWebView;
    // intent
    private String title, article, img, videoID;

    //admob banner
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_show);

        initViews();
        getValues();

        setSupportActionBar(pageToolbar);
        getSupportActionBar().setTitle(title);
        //pageToolbar.setTitleTextColor(getResources().getColor(R.color.white));


        setValues(article);

        loadAdmobBanner();

    }

    private void initViews() {

        pageToolbar = findViewById(R.id.show_page_toolbar);
        pageArticleWebView = findViewById(R.id.show_page_article);
        pageImg = findViewById(R.id.show_page_img);

    }


    private void getValues() {

        Intent intent = getIntent();

        if (intent != null) {

            title = intent.getStringExtra("page_title");
            article = intent.getStringExtra("page_article");
            img = intent.getStringExtra("page_img");
            videoID = intent.getStringExtra("page_video_id");
            //lastUpdate   = intent.getStringExtra("page_update_date");


        }


    }

    private void setValues(String article) {

        //check if page has video
        if (videoID == null || videoID == "") {

            pageImg.setVisibility(View.VISIBLE);
            Picasso.get().load(img).into(pageImg);

        } else {
            showVideo();

        }

        //WebView page article
        pageArticleWebView.getSettings().setJavaScriptEnabled(true);
        pageArticleWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        pageArticleWebView.loadDataWithBaseURL("http://nada", article, "text/html", "utf8", "");

    }

    private void showVideo() {


        YouTubePlayerView youTubePlayerView = findViewById(R.id.show_page_video);

        youTubePlayerView.setVisibility(View.VISIBLE);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = videoID;
                youTubePlayer.cueVideo(videoId, 0);
            }
        });

        getLifecycle().addObserver(youTubePlayerView);


    }


    // admob banners
    private void loadAdmobBanner() {

        mAdView = findViewById(R.id.page_show_adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        Ads ads = new Ads(this);
        ads.checkForConsent();
        adRequest = ads.showBannerAds(adRequest);

        mAdView.loadAd(adRequest);
    }


}