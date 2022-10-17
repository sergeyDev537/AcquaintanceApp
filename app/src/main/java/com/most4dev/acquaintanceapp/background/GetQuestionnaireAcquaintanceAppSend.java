package com.most4dev.acquaintanceapp.background;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import com.most4dev.acquaintanceapp.Config;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetQuestionnaireAcquaintanceAppSend extends AsyncTask<Map<String, String>, Void, String> {

    private final String name;
    private final String theme;
    private final String message;

    public GetQuestionnaireAcquaintanceAppSend(String name, String theme, String message) {
        this.name = name;
        this.theme = theme;
        this.message = message;
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
            GetQuestionAcquaintance clientForecast = new GetQuestionAcquaintance();
            responseCode = clientForecast.run(
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }


    @Override
    protected void onPostExecute(String strPostExecute) {
        super.onPostExecute(strPostExecute);
    }


    public class GetQuestionAcquaintance {
        OkHttpClient okHttpClientAcquaintance = new OkHttpClient();

        String run() throws IOException {
            Request request = new Request.Builder()
                    .url(Config.sendAbuse)
                    .post(postData())
                    .build();
            try (Response response = okHttpClientAcquaintance.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        }

        @SuppressLint("HardwareIds")
        public RequestBody postData() {
            MultipartBody.Builder builderAcquaintance = new MultipartBody.Builder();
            builderAcquaintance.setType(MultipartBody.FORM);
            builderAcquaintance.addFormDataPart("name", name);
            builderAcquaintance.addFormDataPart("theme", theme);
            builderAcquaintance.addFormDataPart("message", message);
            builderAcquaintance.build();
            return builderAcquaintance.build();
        }

    }
}
