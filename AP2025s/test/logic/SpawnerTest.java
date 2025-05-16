package logic;

import data.Connection;
import data.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logic.Spawner;
import logic.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

class SpawnerMockPoint extends Point {
    public SpawnerMockPoint(String name, double x, double y) {
        super(name, x, y);
    }

    @Override
    public String getType() {
        return "MockPoint";
    }
}

public class SpawnerTest {

    private Spawner spawner;
    private Connection connection;

    @BeforeEach
    public void setup() {
        Point from = new MockPoint("A", 0, 0);
        Point to = new MockPoint("B", 100, 0);
        connection = new Connection("A", "B", from, to);
        spawner = new Spawner(from, connection, 5);
    }

    @Test
    public void testShouldSpawn() {
        assertFalse(spawner.shouldSpawn(0), "Should not spawn at time 0");
        assertTrue(spawner.shouldSpawn(5), "Should spawn at time divisible by 5");
        assertFalse(spawner.shouldSpawn(3), "Should not spawn at time 3");
    }

    @Test
    public void testSpawnedVehicleHasCorrectProperties() {
        Vehicle v = spawner.spawnVehicle();

        assertNotNull(v, "Spawned vehicle should not be null");
        assertEquals(connection, v.getCurrentConnection(), "Vehicle should be on the correct connection");

        double speed = v.getSpeed();
        assertTrue(speed > 0, "Speed should be positive");
        assertTrue(speed >= 0 && speed <= 150, "Speed should be within a reasonable range (0-150 km/h)");
    }
}
