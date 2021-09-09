package cn.dsttl3.harmonyos.nongli.utils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OKUtil {
    public String getData(String BASE_URL) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
}
