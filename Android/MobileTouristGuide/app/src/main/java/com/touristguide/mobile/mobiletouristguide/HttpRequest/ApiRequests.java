package com.touristguide.mobile.mobiletouristguide.HttpRequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

import okhttp3.*;

public class ApiRequests
{
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //private static String ServerUrl="http://10.0.2.2:8080/api/";
    private static String ServerUrl="http://ec2-52-10-51-190.us-west-2.compute.amazonaws.com:8080/api/";
    public static void GET(String queryString, final Callback callback) throws Exception{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(ServerUrl + queryString)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                //response 200(OK!) değilse on response a degilde on failure e düşsün.
                if(response.code() == 200)
                    callback.onResponse(call, response);
                else
                    callback.onFailure(call, new IOException());
            }
        });
    }

    public static void POST(String queryString, final Callback callback, String ... params) throws Exception{
        String json="{\"email\":\""+params[0]+"\",\"password\":\""+params[1]+"\"}";
        OkHttpClient client = new OkHttpClient();
        Log.e("params: ",params[0]+" * "+params[1]);
        Log.e("apirequest: ",ServerUrl+queryString);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request=new Request.Builder().url(ServerUrl+queryString).post(requestBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.e("e","0 "+e.getMessage());
                callback.onFailure(call, e);
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                //response 200(OK!) değilse on response a degilde on failure e düşsün.
               // if(response.code() == 200)
                    callback.onResponse(call, response);
               // else{
                //    Log.e("e","1");
                  //  callback.onFailure(call, new IOException());
                //}

            }
        });
    }

    public static Response runSync(String parameters) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ServerUrl + parameters)
                .build();

        Response response = client.newCall(request).execute();
        Log.e("Response",response.body().string());
        return response;
    }


}
