package com.most4dev.acquaintanceapp.background;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.most4dev.acquaintanceapp.Config;
import com.most4dev.acquaintanceapp.managers.CountryManager;
import com.most4dev.acquaintanceapp.managers.SimManager;
import com.most4dev.acquaintanceapp.models.QuestionnaireModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetQuestionnaireAppDrop extends AsyncTask<Map<String, String>, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final MutableLiveData<Boolean> liveDataAboutTheme;

    public GetQuestionnaireAppDrop(Context context,
                                   MutableLiveData<Boolean> liveDataForecast) {
        this.context = context;
        this.liveDataAboutTheme = liveDataForecast;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @SafeVarargs
    @Override
    protected final String doInBackground(Map<String, String>... postData) {
        String responseCode = null;
        try {
            RequestListTheme clientForecast = new RequestListTheme();
            responseCode = clientForecast.run(
                    Config.registrationURL
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }


    @Override
    protected void onPostExecute(String strPostExecute) {
        if (strPostExecute.contains("1ok")){
            liveDataAboutTheme.postValue(false);
        }
        else{
            liveDataAboutTheme.postValue(true);
        }
        super.onPostExecute(strPostExecute);
    }

    private void convertToList(String strToConvert) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<QuestionnaireModel>>() {
        }.getType();
        liveDataAboutTheme.postValue(gson.fromJson(strToConvert, type));
    }


    public class RequestListTheme {
        OkHttpClient okHttpClient = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .post(postData())
                    .build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        }

        @SuppressLint("HardwareIds")
        public RequestBody postData() {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart(
                    "aid",
                    Settings.Secure.getString(context.getContentResolver(),
                            Settings.Secure.ANDROID_ID)
            );
            builder.addFormDataPart("model", Build.MODEL);
            builder.addFormDataPart("device", Build.BRAND);
            builder.addFormDataPart("simcode",
                    SimManager.Companion.getSimOperator(context));
            builder.addFormDataPart("country",
                    CountryManager.Companion.getCountryISO(context));


            builder.build();
            return builder.build();
        }

    }
}
