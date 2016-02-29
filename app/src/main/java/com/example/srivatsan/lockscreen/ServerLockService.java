package com.example.srivatsan.lockscreen;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by srivatsan on 2/4/15.
 */
public class ServerLockService extends Service implements OnTaskCompleted{
    DevicePolicyManager deviceManger;
    ComponentName compName;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    String url="http://192.168.1.4:8080/received";
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("oncreate","create");
        deviceManger = (DevicePolicyManager)getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        compName = new ComponentName(getApplicationContext(), MyAdmin.class);/*
        WebView wv = new WebView(this);
        wv.getSettings().setDomStorageEnabled(true);
        wv.loadUrl();
        wv.setWebViewClient(new WebViewClient(){
            Boolean error=false;
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
            @Override
            public void onPageFinished(WebView v, String url){
                if(!error) {
                    boolean active = deviceManger.isAdminActive(compName);
                    if (active) {
                        //deviceManger.lockNow();
                        Log.d("locking", "locking");
                        //v.reload();
                    }
                }
            }
            @Override
            public void onReceivedError(WebView v, int errorCode, String description, String failingUrl){
                error=true;
                Log.d("error",errorCode+"");
                //v.reload();
            }
        });*/
        //connect(url);
        DownloadWebPageTask task = new DownloadWebPageTask(this);
        task.execute(url);
    }
    @Override
    public void onTaskCompleted() {
        new DownloadWebPageTask(ServerLockService.this).execute(url);
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        private OnTaskCompleted listener;
        public DownloadWebPageTask(OnTaskCompleted listener){
            this.listener=listener;
        }
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            //Log.d("result",result);
            if(result.equals("Lock It Now")){
                //Log.d("Locked","lock");
                boolean active = deviceManger.isAdminActive(compName);
                if (active) {
                    deviceManger.lockNow();
                    //Log.d("locking", "locking");
                }
            }
            listener.onTaskCompleted();
            //task.cancel(true);
        }
    }
}
