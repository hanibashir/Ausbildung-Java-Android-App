package me.hani.ausbildung.extras;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;

import java.net.MalformedURLException;
import java.net.URL;

import me.hani.ausbildung.R;


public class Ads {

    //private InterstitialAd mInterstitialAd;

    private ConsentForm form;

    private Context mContext;

    private boolean isPersonalized;

    public Ads(Context context) {
        mContext = context;
    }


    //--------------- Requesting Consent from European Users -------------//


    public void checkForConsent() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(mContext);

        String[] publisherId = {mContext.getResources().getString(R.string.admob_publisher_id)};
        consentInformation.requestConsentInfoUpdate(publisherId, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                if (ConsentInformation.getInstance(mContext).isRequestLocationInEeaOrUnknown()) {

                    switch (consentStatus) {
                        case PERSONALIZED:
                            isPersonalized = true;
                            break;

                        case NON_PERSONALIZED:
                            isPersonalized = false;
                            break;

                        case UNKNOWN:
                            consentForm();
                            break;
                    }
                } else {
                    // not in EU
                    isPersonalized = true;
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // User's consent status failed to update.
            }
        });
    }

    //======================================


    private void consentForm() {

        URL privacyUrl = null;
        try {
            privacyUrl = new URL("https://hblap.com/apps/ausbildung/ausbildung/privacy_policy.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle error.
        }

        form = new ConsentForm.Builder(mContext, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        form.show();
                    }

                    @Override
                    public void onConsentFormOpened() {

                    }

                    @Override
                    public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        // Consent form closed.
                        if (consentStatus.equals(ConsentStatus.PERSONALIZED)) {
                            //the user chose personalized ads
                            isPersonalized = true;
                        } else {
                            // the user chose non-personalized ads
                            isPersonalized = false;
                        }

                    }
                    // Consent form was closed.


                    @Override
                    public void onConsentFormError(String errorDescription) {

                        // Consent form error
                        Log.d("Consent Form Error: ", errorDescription);

                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        form.load();

    }



    public AdRequest showBannerAds(AdRequest adRequest) {

        if (isPersonalized) {

            adRequest = new AdRequest.Builder().build();

        } else { //show non-personalized ads

            Bundle extras = new Bundle();

            extras.putString("npa", "1");
            adRequest = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
        }

        return adRequest;

    }


}
