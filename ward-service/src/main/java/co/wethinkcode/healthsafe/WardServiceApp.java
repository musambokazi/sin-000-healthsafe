package co.wethinkcode.healthsafe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class WardServiceApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7031);

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Provides lists of wards and departments.)
        // Add domain endpoints for ward-service here.
        List<Ward> wards = fetchWards();
        app.get("/wards", ctx -> ctx.json(wards));
        app.get("/wards/{id}", ctx -> {
            String id = ctx.pathParam("id").toLowerCase();
            Optional<Ward> ward = wards.stream()
                    .filter(w -> w.id().equals(id))
                    .findFirst();
            if (ward.isEmpty()) {
                ctx.status(404)
                        .contentType("application/json")
                        .result("{\"error\":\"ward not found\"}");
            } else {
                ctx.json(ward.get());
            }
        });
    }

    private static List<Ward> fetchWards(){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create("http://localhost:7030/wards"))
                    .GET()
                    .build();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IllegalStateException(
                        "ingestion-service answered " + response.statusCode());
            }
            return new ObjectMapper().readValue(
                    response.body(), new TypeReference<List<Ward>>() {}
            );
        }catch (IOException | InterruptedException e){
            throw new IllegalStateException("Could not fetch wards from ingestion-service", e);
        }
    }
}

// MQ TODO: subscribes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
// MQ TODO: publishes to ActiveMQ queue MqConfig.QUEUE when it detects an equipment failure on one of its wards.
