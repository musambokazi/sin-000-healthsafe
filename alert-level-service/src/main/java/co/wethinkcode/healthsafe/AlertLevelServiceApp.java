package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

import java.util.concurrent.atomic.AtomicInteger;

public class AlertLevelServiceApp {
    private static final AtomicInteger level = new AtomicInteger(0);

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7032);

        app.get("/health", ctx -> ctx.result("OK"));

        // TODO (Tracks the hospital Emergency Status (0-8, 8 = full Code Blue).)
        // Add domain endpoints for alert-level-service here.
        app.get("/alert-level", ctx ->
                ctx.contentType("application/json")
                        .result("{\"level\":" + level + "}"));

        app.put("/alert-level", ctx -> {
            try {
                int newLevel = Integer.parseInt(ctx.body().trim());
                if (!AlertLevel.isValidLevel(newLevel)){
                    ctx.status(400);
                    return;
                }
                level.set(newLevel);
                ctx.contentType("application/json")
                        .result("{\"level\":" + newLevel + "}");
            }catch (NumberFormatException e){
                ctx.status(400);
            }
        });
    }
}
