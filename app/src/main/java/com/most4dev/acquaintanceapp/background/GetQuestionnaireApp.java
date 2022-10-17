package com.most4dev.acquaintanceapp.background;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.most4dev.acquaintanceapp.Config;
import com.most4dev.acquaintanceapp.models.QuestionnaireModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetQuestionnaireApp extends AsyncTask<Map<String, String>, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final MutableLiveData<List<QuestionnaireModel>> liveDataAboutTheme;

    public GetQuestionnaireApp(Context context,
                               MutableLiveData<List<QuestionnaireModel>> liveDataForecast) {
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
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }


    @Override
    protected void onPostExecute(String strPostExecute) {
        convertToList(strPostExecute);
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

        String run() throws IOException {
            Request request = new Request.Builder()
                    .url(Config.questionnaireList)
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
            builder.addFormDataPart("pac", context.getPackageName());
            builder.addFormDataPart(
                    "referrer",
                    PreferenceManager.getDefaultSharedPreferences(context)
                            .getString(context.getPackageName() + "/referrerValue", ""));
            builder.addFormDataPart(
                    "advertisingID",
                    PreferenceManager.getDefaultSharedPreferences(context)
                            .getString(context.getPackageName() +
                                    "/adsKeyApplication", "null"));
            builder.build();
            return builder.build();
        }

    }
}
