package logic;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Connection;

// Counts the number of vehicles on each connection at a given time step.

public class StatisticCalculator {

    public Map<Connection, Integer> calculateVehicleCounts(List<Vehicle> vehicles) {
        Map<Connection, Integer> vehicleCounts = new HashMap<>();

        for (Vehicle v : vehicles) {
            Connection conn = v.getCurrentConnection();
            vehicleCounts.put(conn, vehicleCounts.getOrDefault(conn, 0) + 1);
        }

        return vehicleCounts;
    }
    public StatisticCalculator(List<Connection> allConnections) {

    }

}
