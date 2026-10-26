package co.wethinkcode.healthsafe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlertParser {
    private final ObjectMapper mapper = new ObjectMapper();
    public EquipmentAlert parse(String text) {
        JsonNode node;
        try {
            node = mapper.readTree(text);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("not valid JSON", e);
        }
        return new EquipmentAlert(
                requiredText(node, "wardId"),
                requiredText(node, "equipment"));
    }

    private String requiredText(JsonNode node, String field) {
        if (!node.hasNonNull(field) || node.get(field).asText().isBlank()) {
            throw new IllegalArgumentException(field + " is missing or blank");
        }
        return node.get(field).asText();
    }
}
