package me.hani.ausbildung.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import me.hani.ausbildung.R;
import me.hani.ausbildung.api.RetrofitClientInstance;
import me.hani.ausbildung.extras.CheckConnection;
import me.hani.ausbildung.interfaces.RetrofitInterface;
import me.hani.ausbildung.models.AboutItem;
import retrofit2.Call;
import retrofit2.Callback;

public class AboutFragment extends Fragment {

    private TextView tvAppName, tvThisAppVersion, tvCurrentAppVersion, tvAppDesc, tvPrivacyPolicy;
    private String appName, appDescription, appVersion, email, website, developedBy, privacyPolicy;
    private ProgressBar progressBar;
    private View view;

    private ArrayList<AboutItem> aboutItemArrayList;
    private LinearLayout updateAppLayout,appIsNew;
    private MaterialButton updateBtn;

    private CheckConnection mCheckConnection;
    private WebView webViewDescription, webViewPrivacyPolicy;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("عن هذا التطبيق");

        intViews();
        updateAppLayout.setVisibility(View.GONE);


        mCheckConnection = new CheckConnection(getActivity());
        if (mCheckConnection.isNetworkConnected()) {


            getAboutApp();


        } else {//No Connection

            mCheckConnection.showNoConnectionAlert("لايوجد اتصال بالانترنت",
                    "التطبيق يحتاج للاتصال بشبكة الانترنت لإظهار هذه المعلومات.", "المحاولة ثانية", "إلغاء");

        }


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });

    }


    private void intViews() {
        View view = getView();

        if (view != null) {
            tvAppName = view.findViewById(R.id.about_app_name);

            tvThisAppVersion = view.findViewById(R.id.this_app_version);
            tvCurrentAppVersion = view.findViewById(R.id.current_app_version);

            updateAppLayout = view.findViewById(R.id.layout_update_app);

            updateBtn = view.findViewById(R.id.btn_update_app);
            appIsNew = view.findViewById(R.id.app_is_new);


            //WebView Description
            webViewDescription = view.findViewById(R.id.about_app_description);
            //WebView Privacy Policy
            webViewPrivacyPolicy = view.findViewById(R.id.about_app_privacy_policy);

            progressBar = view.findViewById(R.id.about_pb);


        }
    }


    private void getAboutApp() {

        aboutItemArrayList = new ArrayList<>();

        RetrofitInterface api = RetrofitClientInstance.getRetrofitInstance().create(RetrofitInterface.class);
        Call<ArrayList<AboutItem>> call = api.getAbout();
        call.enqueue(new Callback<ArrayList<AboutItem>>() {
            @Override
            public void onResponse(Call<ArrayList<AboutItem>> call, retrofit2.Response<ArrayList<AboutItem>> response) {
                aboutItemArrayList = response.body();
                appName = aboutItemArrayList.get(0).getAppName();
                appDescription = aboutItemArrayList.get(0).getAppDescription();
                appVersion = aboutItemArrayList.get(0).getAppVersion();
                email = aboutItemArrayList.get(0).getEmail();
                website = aboutItemArrayList.get(0).getWebsite();
                privacyPolicy = aboutItemArrayList.get(0).getAppPrivacyPolicy();

                tvAppName.setText(appName);

                tvCurrentAppVersion.setText(appVersion);

//                String[] thisAppVersion = tvThisAppVersion.getText().toString().split("\\.");
//                String[] currentAppVersion = appVersion.split("\\.");
//
//                Log.d("currentAppVersion", currentAppVersion[0]);
//                Log.d("thisAppVersion", thisAppVersion[0]);
//
//                if (thisAppVersion.length != currentAppVersion.length) {
//                    return;
//                }
//
//                for (int position = 0; position < thisAppVersion.length; position++) {
//                    // compare v1[pos] with v2[pos] as necessary
//                    if (Integer.parseInt(currentAppVersion[position]) > Integer.parseInt(thisAppVersion[position])) {
//
//                        updateAppLayout.setVisibility(View.VISIBLE);
//                        appIsNew.setVisibility(View.GONE);
//                    } else{
//                        updateAppLayout.setVisibility(View.GONE);
//                        appIsNew.setVisibility(View.VISIBLE);
//                    }
//
//                }


                //WebView Description
                webViewDescription.getSettings().setJavaScriptEnabled(true);
                webViewDescription.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                webViewDescription.loadDataWithBaseURL("http://nada", appDescription, "text/html", "utf8", "");

                //WebView Privacy Policy
                webViewPrivacyPolicy.getSettings().setJavaScriptEnabled(true);
                webViewPrivacyPolicy.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                webViewPrivacyPolicy.loadDataWithBaseURL("http://nada", privacyPolicy, "text/html", "utf8", "");

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ArrayList<AboutItem>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getActivity(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
