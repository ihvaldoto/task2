import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Task4 {

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/anything"))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        String body = response.body();

        System.out.println("=== Тело ответа ===");
        System.out.println(body);
        System.out.println();

        // Ищем в теле ответа поле "Accept"
        // httpbin/anything возвращает заголовки запроса в секции "headers"
        int headersIdx = body.indexOf("\"headers\"");
        if (headersIdx == -1) {
            System.out.println("Секция headers не найдена");
            return;
        }

        int acceptIdx = body.indexOf("\"Accept\"", headersIdx);
        if (acceptIdx == -1) {
            System.out.println("Заголовок Accept не найден в теле ответа");
            return;
        }

        // Формат: "Accept": "application/json"
        int valueStart = body.indexOf('"', acceptIdx + 9) + 1;
        int valueEnd = body.indexOf('"', valueStart);
        String acceptValue = body.substring(valueStart, valueEnd);

        System.out.println("=== Значение заголовка Accept ===");
        System.out.println("Accept: " + acceptValue);
    }
}