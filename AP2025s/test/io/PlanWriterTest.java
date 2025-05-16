package io;
import data.Connection;
import data.EntryPoint;
import data.IntersectionPoint;
import data.Point;
import io.PlanWriter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlanWriterTest {

    @Test
    public void testValidConnectionCreation() {
        Map<String, Point> points = new HashMap<>();
        points.put("A", new EntryPoint("A", 0, 0, "B", 1));

        Map<String, Integer> bTargets = new HashMap<>();
        bTargets.put("C", 1);
        points.put("B", new IntersectionPoint("B", 100, 0, bTargets));
        points.put("C", new IntersectionPoint("C", 200, 0, new HashMap<>()));

        PlanWriter writer = new PlanWriter(points);
        List<Connection> connections = writer.getConnections();

        assertEquals(2, connections.size(), "Expected 2 connections: A->B and B->C");

        boolean abExists = connections.stream().anyMatch(c -> c.getFrom().equals("A") && c.getTo().equals("B"));
        boolean bcExists = connections.stream().anyMatch(c -> c.getFrom().equals("B") && c.getTo().equals("C"));

        assertTrue(abExists, "Connection A -> B should exist");
        assertTrue(bcExists, "Connection B -> C should exist");
    }

    @Test
    public void testDuplicateConnectionIgnored() {
        Map<String, Point> points = new HashMap<>();

        Map<String, Integer> targets = new HashMap<>();
        targets.put("B", 1);

        points.put("A", new IntersectionPoint("A", 0, 0, targets));
        points.put("B", new IntersectionPoint("B", 100, 0, new HashMap<>())) ;

        // Add the same target again to simulate a duplicate
        targets.put("B", 2);

        PlanWriter writer = new PlanWriter(points);
        List<Connection> connections = writer.getConnections();

        assertEquals(1, connections.size(), "Only one connection A->B should be created despite duplicate target");
    }

    @Test
    public void testInvalidTargetNotConnected() {
        Map<String, Point> points = new HashMap<>();
        points.put("A", new EntryPoint("A", 0, 0, "Z", 1)); // "Z" doesn't exist

        PlanWriter writer = new PlanWriter(points);
        List<Connection> connections = writer.getConnections();

        assertEquals(0, connections.size(), "No connection should be created when target does not exist");
    }
}
