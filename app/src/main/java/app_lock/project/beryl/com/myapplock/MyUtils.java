package app_lock.project.beryl.com.myapplock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Shadab Aazam on 11/09/2018.
 */
public class MyUtils {
    /**
     * Checks if user has internet connectivity
     *
     * @param context
     * @return
     */
    public static boolean isInternetConnected(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            return true;
        else
            return false;
    }
}
