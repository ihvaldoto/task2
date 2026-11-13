import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.Strictness;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Task4 {
    private static final URI URL = URI.create("https://httpbin.org/anything");
    private static final Gson GSON = new GsonBuilder().setStrictness(Strictness.STRICT).create();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(fetchAccept());
    }

    static String fetchAccept() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URL)
                .timeout(Duration.ofSeconds(15))
                .header("Accept", "application/json")
                .GET()
                .build();

        try (HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("httpbin.org вернул HTTP " + response.statusCode());
            }
            return readAccept(response.body());
        }
    }

    static String readAccept(String body) throws IOException {
        JsonElement response;
        try {
            response = GSON.fromJson(body, JsonElement.class);
        } catch (JsonParseException e) {
            throw new IOException("Ответ httpbin.org содержит некорректный JSON", e);
        }

        if (response == null || !response.isJsonObject()) {
            throw new IOException("Ожидался JSON-объект в ответе httpbin.org");
        }
        JsonElement headers = response.getAsJsonObject().get("headers");
        if (headers == null || !headers.isJsonObject()) {
            throw new IOException("В ответе httpbin.org отсутствует объект headers");
        }
        JsonElement accept = headers.getAsJsonObject().get("Accept");
        if (accept == null || !accept.isJsonPrimitive() || !accept.getAsJsonPrimitive().isString()) {
            throw new IOException("В объекте headers отсутствует строковое поле Accept");
        }
        return accept.getAsString();
    }
}
