package logic;

import data.Connection;
import data.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logic.StatisticsEntry;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsEntryTest {

    private StatisticsEntry entry;

    @BeforeEach
    public void setup() {
        Point a = new MockPoint("A", 0, 0);
        Point b = new MockPoint("B", 200, 0); // distance = 200
        Connection conn = new Connection("A", "B", a, b);
        entry = new StatisticsEntry(conn);
    }

    @Test
    public void testTotalVehiclesPer100m() {
        entry.vehicleOnStep(); // 1 vehicle
        entry.vehicleOnStep(); // 2 vehicles
        entry.finalizeStep();  // total += 2

        double expected = 2 / (200.0 / 100); // 2 per 200m -> 1 per 100m
        assertEquals(expected, entry.getTotalPer100m(), 0.0001);
    }

    @Test
    public void testMaxVehiclesPerStep() {
        entry.vehicleOnStep();
        entry.finalizeStep(); // 1

        entry.vehicleOnStep();
        entry.vehicleOnStep();
        entry.finalizeStep(); // 2

        double expectedMax = 2 / (200.0 / 100); // max was 2
        assertEquals(expectedMax, entry.getMaxPer100m(), 0.0001);
    }

    @Test
    public void testResetPerStep() {
        entry.vehicleOnStep();
        entry.finalizeStep();

        // No vehicle this step
        entry.finalizeStep();

        double total = entry.getTotalPer100m();
        double max = entry.getMaxPer100m();

        assertTrue(total > 0);
        assertTrue(max > 0);
    }
}
