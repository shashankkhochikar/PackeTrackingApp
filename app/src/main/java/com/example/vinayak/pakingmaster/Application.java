package com.example.vinayak.pakingmaster;

import android.annotation.SuppressLint;
//mport android.net.http.RequestQueue;
import android.os.Environment;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.example.vinayak.pakingmaster.utils.SessionManager;
import com.example.vinayak.pakingmaster.volley.OkHttpStack;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Application extends android.app.Application {

    public static final String PACKAGE_NAME = Application.class.getPackage().getName();
    private static final String TAG = Application.class.getSimpleName();
    public static String APP_VERSION = "0.1";
    public static String ANDROID_ID = "0000000000000000";
    private static Application mInstance;
    private RequestQueue mRequestQueue;
    private SessionManager sessionManager;

    public static synchronized Application getInstance() {
        return mInstance;
    }

    /**
     * Method provides defaultRetryPolice.
     * First Attempt = 14+(14*1)= 28s.
     * Second attempt = 28+(28*1)= 56s.
     * then invoke Response.ErrorListener callback.
     *
     * @return DefaultRetryPolicy object
     */
    public static DefaultRetryPolicy getDefaultRetryPolice() {
        return new DefaultRetryPolicy(14000, 2, 1);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        sessionManager = new SessionManager(getApplicationContext());

        handleSSLHandshake();


        // FontsOverride.setDefaultFont(this, "DEFAULT");
        //  FontsOverride.setDefaultFont(this, "MONOSPACE");
        // FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset3.ttf");
        // FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");
//        getFirebaseToken();
    }

    private void getFirebaseToken() {
    }

    //////////////////////// Volley request ///////////////////////////////////////////////////////////////////////////////////////
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack());
        }
        return mRequestQueue;
    }

    @VisibleForTesting
    public void setRequestQueue(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}
