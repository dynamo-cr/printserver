package com.printserver.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GetLabelsFromPowerAutomate {
    public static Response getfromPowerAutomate(String printer, String getExternalWebServiceUrl) throws IOException {
        JSONObject json = new JSONObject();
        json.put("printer", printer);
        String requestBody1 = json.toString();
        OkHttpClient client = (new OkHttpClient.Builder()).build();
        Headers headers = (new Headers.Builder()).add("Api-key", PrintServerProcess.configuration("dyn.apikey")).build();
        String contentType = "application/json; charset=utf-8";
        MediaType mediaType = MediaType.parse(contentType);
        RequestBody body = RequestBody.create(mediaType, requestBody1.getBytes(StandardCharsets.UTF_8));
        Request request = (new Request.Builder()).url(getExternalWebServiceUrl).headers(headers).method("POST", body).build();
        return client.newCall(request).execute();
    }
}
