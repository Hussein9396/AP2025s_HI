package io;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import data.Connection;
import data.IntersectionData;
import data.Point;
import data.SpawnerData;

public class PlanWriter {

    private final Map<String, Point> points;
    private final Set<String> connectionsSet = new HashSet<>();
    private final List<Connection> connections = new ArrayList<>();
    private final Map<String, Connection> connectionMap = new HashMap<>();   // ✅ NEU für Referenz-Sicherheit


    /**
     * Konstruktor → übernimmt Daten vom InputParser
     */
    public PlanWriter(Map<String, Point> points, List<IntersectionData> intersections, List<SpawnerData> spawners) {
        this.points = points;
        buildConnections(intersections);
        addSpawnerConnections(spawners);   // ➕ EP → Kreuzung Verbindungen
    }

    /**
     * Verbindungen Kreuzung → Kreuzung
     */
    private void buildConnections(List<IntersectionData> intersections) {
        for (IntersectionData kd : intersections) {
            String from = kd.getName();
            if (!points.containsKey(from)) continue;

            for (String target : kd.getTargets().keySet()) {
                addConnection(from, target);
            }
        }
    }

    /**
     * Verbindungen Einfallspunkt → Kreuzung
     */
    private void addSpawnerConnections(List<SpawnerData> spawners) {
        for (SpawnerData spawner : spawners) {
            addConnection(spawner.getName(), spawner.getTargetIntersecion());
        }
    }

    /**
     * Verbindung hinzufügen + gleichzeitig connectionMap pflegen
     */
    private void addConnection(String from, String to) {
        String key = from + "-" + to;

        if (!connectionsSet.contains(key) &&
            points.containsKey(from) && points.containsKey(to)) {

            Point fromPoint = points.get(from);
            Point toPoint = points.get(to);

            if (fromPoint != null && toPoint != null) {
                Connection conn = new Connection(from, to, fromPoint, toPoint);
                connections.add(conn);
                connectionsSet.add(key);
                connectionMap.put(key, conn);     // ✅ NEU → für Spawner + Statistics
            }
        }
    }

    /**
     * Gibt alle Verbindungen zurück
     */
    public List<Connection> getConnections() {
        return connections;
    }

    /**
     * Gibt die Connection-Map zurück (für Spawner in App)
     */
    public Map<String, Connection> getConnectionMap() {
        return connectionMap;
    }


    /**
     * Plan.txt für Plot.py erzeugen
     */
    public void writePlanFile(String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (Connection c : connections) {
                writer.write(c.getFromPoint().getX()/100 + " " + c.getFromPoint().getY()/100 + " " +
                             c.getToPoint().getX()/100 + " " + c.getToPoint().getY()/100);
                writer.newLine();

            }
            System.out.println("Plan.txt erfolgreich geschrieben.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
