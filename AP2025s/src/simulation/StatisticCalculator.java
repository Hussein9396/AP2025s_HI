package simulation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.Connection;

public class StatisticCalculator {

    private List<Connection> connections;

    /**
     * Konstruktor → bekommt alle Connections
     */
    public StatisticCalculator(List<Connection> connections) {
        this.connections = connections;
    }

    /**
     * Ausgabe aller Verbindungen
     */
    public void printConnections() {
        System.out.println("=== Verbindungen ===");
        for (Connection c : connections) {
            System.out.printf("%s -> %s (from: %.2f,%.2f -> to: %.2f,%.2f)\n",
                    c.getFrom(), c.getTo(),
                    c.getFromPoint().getX(), c.getFromPoint().getY(),
                    c.getToPoint().getX(), c.getToPoint().getY());
        }
    }

    /**
     * Zählt Fahrzeuge auf allen Strecken
     */
    public Map<Connection, Integer> calculateVehicleCounts(List<Vehicle> vehicles) {
        Map<Connection, Integer> vehicleCounts = new HashMap<>();

        for (Vehicle v : vehicles) {
            Connection conn = v.currentConnection;
            vehicleCounts.put(conn, vehicleCounts.getOrDefault(conn, 0) + 1);
        }

        return vehicleCounts;
    }

    /**
     * Gibt Fahrzeuganzahlen pro Strecke aus
     */
    public void printVehicleCounts(Map<Connection, Integer> vehicleCounts) {
        System.out.println("=== Fahrzeuganzahl pro Strecke ===");
        for (Map.Entry<Connection, Integer> entry : vehicleCounts.entrySet()) {
            Connection c = entry.getKey();
            int count = entry.getValue();
            System.out.printf("%s -> %s : %d Fahrzeuge\n", c.getFrom(), c.getTo(), count);
        }
    }
}
