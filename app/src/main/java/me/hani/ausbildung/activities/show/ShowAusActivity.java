package me.hani.ausbildung.activities.show;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import me.hani.ausbildung.R;
import me.hani.ausbildung.extras.Ads;

public class ShowAusActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbar;
    //views variables
    private TextView ausTitle, ausCertificate, ausDuration, ausSalary1, ausSalary2, ausSalary3, ausSalary4;
    private ImageView ausThumbnail;
    private WebView webViewAusDescription;
    private TableRow mainSalaryRow, salary1Row, salary2Row, salary3Row, salary4Row;
    //intent values
    private String title;
    private String certificate;
    private String duration;
    private String imageUrl;
    private String salary1;
    private String salary2;
    private String salary3;
    private String salary4;
    private String description;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aus_show);


        Toolbar toolbar = findViewById(R.id.aus_info_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("تفاصيل المهنة");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        // Initialize Views
        intView();

        try {
            // set intent values to views
            setValuesToViews();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        loadAdmobBanner();
    }



    // Initialize Views
    private void intView() {
        //---------------------------------------------------------

        //TextView ausTitle = findViewById(R.id.aus_details_title);
        ausTitle = findViewById(R.id.aus_details_title);
        ausCertificate = findViewById(R.id.aus_details_certificate);
        ausDuration = findViewById(R.id.aus_details_duration);
        ausThumbnail = findViewById(R.id.aus_details_thumbnail);

        //TextViews
        ausSalary1 = findViewById(R.id.aus_details_salary1);
        ausSalary2 = findViewById(R.id.aus_details_salary2);
        ausSalary3 = findViewById(R.id.aus_details_salary3);
        ausSalary4 = findViewById(R.id.aus_details_salary4);

        //TableRows
        mainSalaryRow = findViewById(R.id.main_salary_row);
        salary1Row = findViewById(R.id.salary1_row);
        salary2Row = findViewById(R.id.salary2_row);
        salary3Row = findViewById(R.id.salary3_row);
        salary4Row = findViewById(R.id.salary4_row);


        webViewAusDescription = findViewById(R.id.aus_details_description);

        //Button ausLinkBtn = findViewById(R.id.aus_details_link);
    }


    // get intent values
    private void getIntentValues() throws URISyntaxException {
        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        String ar_title = intent.getStringExtra("ar_title");
        certificate = intent.getStringExtra("certificate");
        duration = intent.getStringExtra("duration");
        imageUrl = intent.getStringExtra("img_url");

        salary1 = intent.getStringExtra("salary1");
        salary2 = intent.getStringExtra("salary2");
        salary3 = intent.getStringExtra("salary3");
        salary4 = intent.getStringExtra("salary4");

        description = intent.getStringExtra("description");

        link = intent.getStringExtra("aus_link");

        // store the comma separated links string values into list of string
        String[] links = link.split("\\s*,\\s*");
        List<String> linksList = Arrays.asList(links);

        // send the links list to create buttons
        linksBtns(linksList);

    }

    // set intent values to views
    private void setValuesToViews() throws URISyntaxException {

        //get intent values
        getIntentValues();

        // ausTitle.setText(title);

        collapsingToolbar.setTitle(title);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ausTitle.setText(title);
        ausCertificate.setText(certificate);
        ausDuration.setText(duration);
        Picasso.get().load(imageUrl).into(ausThumbnail);

        checkSalary();

        //WebView Aus Description
        webViewAusDescription.getSettings().setJavaScriptEnabled(true);
        //webViewAusDescription.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webViewAusDescription.loadDataWithBaseURL("http://nada", description, "text/html", "utf8", "");
    }

    private void checkSalary() {

        if (salary1.equals("") || salary1.equals("0")) {
            salary1Row.setVisibility(View.GONE);
        } else {
            ausSalary1.setText(salary1);
        }
        if (salary2.equals("") || salary2.equals("0")) {
            salary2Row.setVisibility(View.GONE);
        } else {
            ausSalary2.setText(salary2);
        }
        if (salary3.equals("") || salary3.equals("0")) {
            salary3Row.setVisibility(View.GONE);
        } else {
            ausSalary3.setText(salary3);
        }
        if (salary4.equals("") || salary4.equals("0")) {
            salary4Row.setVisibility(View.GONE);
        } else {
            ausSalary4.setText(salary4);
        }

        if (salary1.equals("0") && salary2.equals("0") && salary3.equals("0") && salary4.equals("0")) {

            mainSalaryRow.setVisibility(View.GONE);
        }
    }

    // create buttons according to aus links List of strings from the api
    private void linksBtns(List<String> links) throws URISyntaxException {

        //Aus Link Button Layout
        LinearLayout linksLayout = findViewById(R.id.links_btns);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 10, 20, 20);

        // Button shape
//        GradientDrawable shape = new GradientDrawable();
//        shape.setColor(getResources().getColor(R.color.colorPrimary));
//        shape.setCornerRadius(50);

        //loop through the coming values of aus links
        for (int i = 0; i < links.size(); i++) {
            // create button
            Button linkBtn = new Button(ShowAusActivity.this);
            linkBtn.setText(" " + getDomainName(links.get(i)).toUpperCase() + " ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                linkBtn.setBackground(getResources().getDrawable(R.drawable.table_layout_gradiant_background, null));
            }
            linkBtn.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                linkBtn.setTextColor(getResources().getColor(R.color.white, null));
            }
            linksLayout.addView(linkBtn);

            // get the link string i.e. "https://ausbildung.de/.../..."
            String linkString = links.get(i);
            //Log.d("aus_link", finalLink);

            // on button clicked ...
            linkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // open it in custom tabs
                    //configure CustomTabsIntent.
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    // set custom tabs app bar background color
                    builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    // Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                    CustomTabsIntent customTabsIntent = builder.build();
                    //convert the link string to uri
                    Uri finalLink = Uri.parse(linkString);
                    // launch it
                    customTabsIntent.launchUrl(ShowAusActivity.this, finalLink);


                }
            });
        }
    }


    // split the domain name from aus link to show it on the button
    private String getDomainName(String link) throws URISyntaxException {
        URI uri = new URI(link);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }



    // admob banners
    private void loadAdmobBanner() {

        //admob banner
        AdView mAdView = findViewById(R.id.aus_show_adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        Ads ads = new Ads(this);
        ads.checkForConsent();
        adRequest = ads.showBannerAds(adRequest);

        mAdView.loadAd(adRequest);
    }

}
