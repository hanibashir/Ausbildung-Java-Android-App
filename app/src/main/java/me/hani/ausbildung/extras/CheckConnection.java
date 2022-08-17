package me.hani.ausbildung.extras;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;

import me.hani.ausbildung.R;
import me.hani.ausbildung.activities.MainActivity;

public class CheckConnection {

    private Context mContext;


    public CheckConnection(Context context) {
        mContext = context;
    }


    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected() == true)
        {
            return true;
        }

        return false;
    }


    public void showNoConnectionAlert(String title, String msg, String positiveText, String negativeText){
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goToHome = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(goToHome);
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) mContext).onBackPressed();
                        ((Activity) mContext).finish();
                    }
                })
                .setIcon(mContext.getResources().getDrawable(R.drawable.ic_signal_wifi_off))
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                                        /*Intent goToHome = new Intent(SplashActivity.this, MainActivity.class);
                                        startActivity(goToHome);*/
                            ((Activity) mContext).onBackPressed();
                            ((Activity) mContext).finish();
                            return true;
                        }
                        return false;
                    }
                })
                .show();
    }
}
