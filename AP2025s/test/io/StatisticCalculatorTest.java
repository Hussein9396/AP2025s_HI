package logic;

import data.Connection;
import data.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logic.StatisticCalculator;
import logic.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticCalculatorTest {

    private Connection connection1;
    private Connection connection2;
    private StatisticCalculator calculator;

    @BeforeEach
    public void setup() {
        Point a = new MockPoint("A", 0, 0);
        Point b = new MockPoint("B", 100, 0);
        Point c = new MockPoint("C", 200, 0);

        connection1 = new Connection("A", "B", a, b);
        connection2 = new Connection("B", "C", b, c);

        List<Connection> allConnections = List.of(connection1, connection2);
        calculator = new StatisticCalculator(allConnections);
    }

    @Test
    public void testEmptyVehicleList() {
        List<Vehicle> vehicles = new ArrayList<>();
        Map<Connection, Integer> result = calculator.calculateVehicleCounts(vehicles);

        assertTrue(result.isEmpty(), "No vehicles should result in empty map");
    }

    @Test
    public void testVehicleCountPerConnection() {
        List<Vehicle> vehicles = new ArrayList<>();

        Vehicle v1 = new Vehicle(1, 50, connection1);
        Vehicle v2 = new Vehicle(2, 40, connection1);
        Vehicle v3 = new Vehicle(3, 30, connection2);

        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);

        Map<Connection, Integer> result = calculator.calculateVehicleCounts(vehicles);

        assertEquals(2, result.get(connection1));
        assertEquals(1, result.get(connection2));
    }
}
