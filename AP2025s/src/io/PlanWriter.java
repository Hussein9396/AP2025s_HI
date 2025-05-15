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
    private final Map<String, Connection> connectionMap = new HashMap<>();



    public PlanWriter(Map<String, Point> points, List<IntersectionData> intersections, List<SpawnerData> spawners) {
        this.points = points;
        buildConnections(intersections);
        addSpawnerConnections(spawners);
    }

    private void buildConnections(List<IntersectionData> intersections) {
        for (IntersectionData kd : intersections) {
            String from = kd.getName();
            if (!points.containsKey(from)) continue;

            for (String target : kd.getTargets().keySet()) {
                addConnection(from, target);
            }
        }
    }


    private void addSpawnerConnections(List<SpawnerData> spawners) {
        for (SpawnerData spawner : spawners) {
            addConnection(spawner.getName(), spawner.getTargetIntersecion());
        }
    }


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
                connectionMap.put(key, conn);
            }
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }


    public Map<String, Connection> getConnectionMap() {
        return connectionMap;
    }

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
