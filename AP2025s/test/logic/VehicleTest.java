package logic;
import data.Connection;
import data.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logic.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

class MockPoint extends Point {
    public MockPoint(String name, double x, double y) {
        super(name, x, y);
    }

    @Override
    public String getType() {
        return "MockPoint";
    }
}

public class VehicleTest {

    private Connection connection;

    @BeforeEach
    public void setup() {
        Point from = new MockPoint("A", 0, 0);
        Point to = new MockPoint("B", 100, 0); // 100 meters distance
        connection = new Connection("A", "B", from, to);
    }

    @Test
    public void testVehicleMovesCorrectly() {
        Vehicle vehicle = new Vehicle(1, 36.0, connection); // 36 km/h = 10 m/s
        vehicle.move(1.0); // 1 second

        assertEquals(0.1, vehicle.getPositionOnConnection(), 0.0001, "Expected progress of 10m on 100m = 0.1");
    }

    @Test
    public void testVehicleDoesNotMoveWithZeroSpeed() {
        Vehicle vehicle = new Vehicle(2, 0.0, connection);
        vehicle.move(1.0);

        assertEquals(0.0, vehicle.getPositionOnConnection(), "Vehicle with speed 0 should not move");
    }

    @Test
    public void testReachedEndOfConnection() {
        Vehicle vehicle = new Vehicle(3, 100.0, connection);
        vehicle.setPositionOnConnection(1.0);

        assertTrue(vehicle.reachedEndOfConnection(), "Position >= 1.0 should mean vehicle reached end");

        vehicle.setPositionOnConnection(0.999);
        assertFalse(vehicle.reachedEndOfConnection(), "Position < 1.0 should mean vehicle not yet at end");
    }
}
