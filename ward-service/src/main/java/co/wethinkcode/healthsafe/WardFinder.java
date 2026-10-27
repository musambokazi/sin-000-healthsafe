package co.wethinkcode.healthsafe;

import java.util.List;
import java.util.Optional;

public class WardFinder {
    public static Optional<Ward> findById(List<Ward> wards, String id) {
        String wanted = id.toLowerCase();
        return wards.stream()
                .filter(w -> w.id().equals(wanted))
                .findFirst();
    }
}
