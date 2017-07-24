package com.example.user.githubsearch;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by User on 23.07.2017.
 */

public class ConnectionChecker {
    Context context;

    public ConnectionChecker(Context context) {
        this.context = context;
    }

    public Boolean isConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return NetworkInfo.State.CONNECTED.equals(networkInfo.getState());
            }
        }
        return false;
    }
}
