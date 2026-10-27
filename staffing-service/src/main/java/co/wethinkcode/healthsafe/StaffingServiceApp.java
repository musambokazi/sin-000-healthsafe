package co.wethinkcode.healthsafe;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class StaffingServiceApp {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String WARD_URL =
            System.getenv().getOrDefault("WARD_SERVICE_URL", "http://localhost:7031");
    private static final String ALERT_URL =
            System.getenv().getOrDefault("ALERT_LEVEL_URL", "http://localhost:7032");

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7033);

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Provides on-call schedules for doctors based on ward and status.)
        // Add domain endpoints for staffing-service here.
        app.get("/staffing/{wardId}", ctx -> {
                    String wardId = ctx.pathParam("wardId").toLowerCase();
                    try {
                        HttpResponse<String> ward = get(WARD_URL + "/wards/" + wardId);
                        if (ward.statusCode() == 404) {
                            ctx.status(404).contentType("application/json")
                                    .result("{\"error\":\"ward not found\"}");
                            return;
                        }
                        HttpResponse<String> alert = get(ALERT_URL + "/alert-level");
                        int level = mapper.readTree(alert.body()).get("level").asInt();

                        int doctors = StaffingRules.doctorsOnCall(level);
                        ctx.json(new StaffingAnswer(wardId, level, doctors));
                    } catch (IOException | InterruptedException | IllegalArgumentException e) {
                        ctx.status(502).contentType("application/json")
                                .result("{\"error\":\"could not work out staffing\"}");
                    }
                });

    }

    private static HttpResponse<String> get(String url) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

// MQ TODO: publishes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
