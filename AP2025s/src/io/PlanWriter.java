package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import data.Connection;
import data.EntryPoint;
import data.IntersectionPoint;
import data.Point;

public class PlanWriter {

    private final Map<String, Point> points;
    private final Set<String> connectionsSet = new HashSet<>();
    private final List<Connection> connections = new ArrayList<>();
    private final Map<String, Connection> connectionMap = new HashMap<>();

    public PlanWriter(Map<String, Point> points) {
        this.points = points;
        buildConnectionsFromIntersections();
        buildConnectionsFromEntryPoints();
    }

    //Creates connections between intersections based on the map definition.
    private void buildConnectionsFromIntersections() {
        for (Point point : points.values()) {
            if (point instanceof IntersectionPoint intersection) {
                String from = intersection.getName();

                for (String target : intersection.getTargets().keySet()) {
                    addConnection(from, target);
                }
            }
        }
    }

    //Creates connections from entry points to their target intersections.
    private void buildConnectionsFromEntryPoints() {
        for (Point point : points.values()) {
            if (point instanceof EntryPoint entryPoint) {
                addConnection(entryPoint.getName(), entryPoint.getTargetIntersection());
            }
        }
    }

    // Adds a connection between two points if it doesn't already exist.
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
    
    // Writes coordinates of connections to a file.
    public void writePlanFile(String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (Connection c : connections) {
                writer.write(c.getFromPoint().getX() / 100 + " " + c.getFromPoint().getY() / 100 + " " +
                c.getToPoint().getX() / 100 + " " + c.getToPoint().getY() / 100);
                writer.newLine();
            }
            System.out.println("Plan.txt erfolgreich geschrieben.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Connection> getConnections() {
        return connections;
    }
    public Map<String, Connection> getConnectionMap() {
        return connectionMap;
    }
}