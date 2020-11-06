/*
package com.example.driver.ui;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;

import androidx.annotation.RequiresApi;

public class UploadImageApacheHttp {
    public static final String TAG = "Upload Image Apache";

    public void doFileUpload(final String url, final String bmp, final Handler handler){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i(TAG, "Starting Upload...");
                final ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                boolean image = nameValuePairs.add(new BasicNameValuePair("image", bmp));

                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(url);
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpclient.execute(httppost);
                    String responseStr = EntityUtils.toString(response.getEntity());
                    Log.i(TAG, "doFileUpload Response : " + responseStr);
                    handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    System.out.println("Error in http connection " + e.toString());
                    handler.sendEmptyMessage(0);
                }
            }
        });
        t.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Base64.Encoder convertBitmapToString(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        Base64.Encoder imageStr = Base64.getUrlEncoder();
        return imageStr;
    }
}

*/
