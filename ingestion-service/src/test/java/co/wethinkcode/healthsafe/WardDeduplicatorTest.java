package co.wethinkcode.healthsafe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WardDeduplicatorTest {
    private final WardDeduplicator deduplicator = new WardDeduplicator();

    private Ward ward(String id, String wing, Integer beds){
        return new Ward(id, wing, "Paediatrics", beds);
    }

    @Test
    void differentIdsAreBothKept(){
        List<Ward> result = deduplicator.deduplicate(List.of(ward("w-01", "East Wing", 3),
                ward("w-02", "West Wing", 4)));
        assertEquals(2, result.size());
        assertEquals("w-01", result.get(0).id());
        assertEquals("w-02", result.get(1).id());
    }

    @Test
    void sameIdTwiceGivesOneWard(){
        List<Ward> result = deduplicator.deduplicate(List.of(
                ward("w-05", "East Wing", 5),
                ward("w-05", "East Wing", 5)));
        assertEquals(1, result.size());
    }

    @Test
    void betterDataWins() {
        List<Ward> result = deduplicator.deduplicate(List.of(
                ward("w-05", "East Wing", null),
                ward("w-05", "East Wing", 5)));
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).bedsAvailable());
    }

    @Test
    void betterDataWinsInReversedOrder() {
        List<Ward> result = deduplicator.deduplicate(List.of(
                ward("w-05", "East Wing", 5),
                ward("w-05", "East Wing", null)));
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).bedsAvailable());
    }

    @Test
    void tieKeepsTheFirst() {
        List<Ward> result = deduplicator.deduplicate(List.of(
                ward("w-05", "Wing A", null),
                ward("w-05", "Wing B", null)));
        assertEquals(1, result.size());
        assertEquals("Wing A", result.get(0).wing());
        assertNull(result.get(0).bedsAvailable());
    }
}
