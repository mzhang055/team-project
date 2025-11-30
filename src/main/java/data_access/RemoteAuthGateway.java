package data_access;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.PublicKey;

public class RemoteAuthGateway {
    private static final String BASE_URL = "http://vm003.teach.cs.toronto.edu:20112";

    private static final int SUCCESS_CODE = 200;

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON  = "application/json";

    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    public boolean userExists(String username) {
        try {
            JSONObject response = getUserJson(username);
            return response != null && response.optInt(STATUS_CODE_LABEL) == SUCCESS_CODE;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPassword(String username) {
        try {
            JSONObject response = getUserJson(username);
            if (response == null || response.optInt(STATUS_CODE_LABEL) != SUCCESS_CODE) return null;
            JSONObject userJson = response.getJSONObject("user");
            return userJson.getString(PASSWORD_KEY);
        } catch (Exception e) {
            return null;
        }
    }

    public void createRemoteUser(String username, String password) throws IOException, JSONException {
        JSONObject body = new JSONObject();
        body.put(USERNAME_KEY, username);
        body.put(PASSWORD_KEY, password);

        RequestBody requestBody = RequestBody.create(body.toString(), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "/user")
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .put(requestBody)
                .build();
        client.newCall(request).execute().close();
    }

    private JSONObject getUserJson(String username) throws IOException, JSONException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/user")
                .newBuilder()
                .addQueryParameter("username", username)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful() || response.body() == null) {
            return null;
        }
        String bodyStr = response.body().string();
        response.close();

        return new JSONObject(bodyStr);
    }
}
