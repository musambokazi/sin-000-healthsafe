package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WardFinderTest {
    private final List<Ward> wards = List.of(
            new Ward("w-05", "East Wing", "Paediatrics", 5),
            new Ward("w-06", "South Wing", "Radiology", 2));

    @Test
    void findsWardByExactId() {
        assertEquals("w-05", WardFinder.findById(wards, "w-05").orElseThrow().id());
    }

    @Test
    void idCaseDoesNotMatter() {
        assertTrue(WardFinder.findById(wards, "W-05").isPresent());
    }

    @Test
    void longerIdIsNotFound() {
        assertTrue(WardFinder.findById(wards, "w-05-99").isEmpty());
    }

    @Test
    void shorterIdIsNotFound() {
        assertTrue(WardFinder.findById(wards, "w-0").isEmpty());
    }
}
