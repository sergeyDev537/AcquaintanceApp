package com.most4dev.acquaintanceapp.background;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import com.most4dev.acquaintanceapp.Config;
import com.most4dev.acquaintanceapp.models.PersonModel;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendPerson extends AsyncTask<Map<String, String>, Void, String> {

    private final PersonModel personModel;

    public SendPerson(PersonModel personModel) {
        this.personModel = personModel;
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
                    Config.sendPerson
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
                    "name",
                    personModel.getName()
            );
            builder.addFormDataPart(
                    "gender",
                    personModel.getGender()
            );
            builder.addFormDataPart(
                    "imageCode",
                    personModel.getImageCode()
            );
            builder.addFormDataPart(
                    "birthday",
                    personModel.getBirthday()
            );
            builder.addFormDataPart(
                    "about",
                    personModel.getAbout()
            );
            builder.build();
            return builder.build();
        }

    }
}
