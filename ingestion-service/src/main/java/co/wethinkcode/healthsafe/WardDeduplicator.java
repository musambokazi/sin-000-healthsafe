package co.wethinkcode.healthsafe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WardDeduplicator {
    public List<Ward> deduplicate(List<Ward> wards) {
        Map<String, Ward> byId = new LinkedHashMap<>();
        for (Ward ward : wards){
            Ward old = byId.get(ward.id());
            if (old == null){
                byId.put(ward.id(),ward);
            } else if (old.bedsAvailable() == null && ward.bedsAvailable() != null) {
                byId.put(ward.id(), ward);
            }
        }
        return new ArrayList<>(byId.values());
    }
}
