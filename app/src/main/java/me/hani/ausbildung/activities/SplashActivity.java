package me.hani.ausbildung.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import me.hani.ausbildung.R;
import me.hani.ausbildung.extras.CheckConnection;

import static com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED;

public class SplashActivity extends AppCompatActivity {

    private int timeOut = 500;
    private CheckConnection mCheckConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mCheckConnection = new CheckConnection(this);

        if (mCheckConnection.isNetworkConnected()) {//if it is connected to the internet

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    //go to home activity
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, timeOut);

        } else {//No Internet

            mCheckConnection.showNoConnectionAlert("لايوجد اتصال بالانترنت",
                    "التطبيق لن يعمل الا بوجود إتصال بالانترنت", "المحاولة ثانية", "إلغاء");
        }
    }
}
