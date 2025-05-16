package io;
import io.InputParser;
import data.EntryPoint;
import data.IntersectionPoint;
import data.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InputParserTest {

    private static final String TEST_FILE = "input/test_input.txt";

    @BeforeEach
    public void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write("""
                Zeitraum:
                50 1

                Einfallspunkte:
                A 1 1 D 10
                B 0 0 D 15
                C 2 0 D 16

                Kreuzungen:
                D 1 0 A 1 B 1 C 1
                """);
        }
    }

    @Test
    public void testValidParsing() {
        InputParser parser = new InputParser(TEST_FILE);
        parser.parse();

        assertEquals(50, parser.getTotalTimeSteps());
        assertEquals(1, parser.getTimeStepInSeconds());

        Map<String, Point> points = parser.getPoints();
        assertEquals(4, points.size());

        assertTrue(points.get("A") instanceof EntryPoint);
        EntryPoint ep = (EntryPoint) points.get("A");
        assertEquals("D", ep.getTargetIntersection());
        assertEquals(10, ep.getSpawnInterval());

        assertTrue(points.get("D") instanceof IntersectionPoint);
        IntersectionPoint ip = (IntersectionPoint) points.get("D");
        assertTrue(ip.getTargets().containsKey("A"));
        assertTrue(ip.getTargets().containsKey("B"));
        assertTrue(ip.getTargets().containsKey("C"));
    }

    // Test for missing Zeitraum
    @Test
    public void testMissingZeitraumThrowsNothingButParsesDefault() throws IOException {
        String file = "input/test_missing_zeitraum.txt";
        Files.writeString(Path.of(file), """
            Einfallspunkte:
            A 1 1 D 10
            B 0 0 D 15
            C 2 0 D 16
            Kreuzungen:
            D 1 0 A 1 B 1 C 1
            """);

        InputParser parser = new InputParser(file);
        parser.parse();

        // Default sollte 0 Schritte und 1 Sekunde sein
        assertEquals(0, parser.getTotalTimeSteps());
        assertEquals(1, parser.getTimeStepInSeconds());
    }

    // Test for invalid SpawnInterval
    @Test
    public void testInvalidSpawnIntervalThrowsNumberFormatException() throws IOException {
        String file = "input/test_invalid_spawn.txt";
        Files.writeString(Path.of(file), """
            Zeitraum:
            50 1
            Einfallspunkte:
            A 0 0 B notanumber
            Kreuzungen:
            B 1 1 C 1
            """);

        InputParser parser = new InputParser(file);
        assertThrows(NumberFormatException.class, parser::parse);
    }

    // Test for invalid coordinates, missing Kreuzung, invalid format
    @Test
    public void testCrashInputWithMalformedLines() throws IOException {
        String file = "input/test_crashing_input.txt";
        Files.writeString(Path.of(file), """
            Zeitraum:
            abc def

            Einfallspunkte:
            A 0 0 B 5
            C 1 1

            Kreuzungen:
            D 0 0 A 2 B
            """);

        InputParser parser = new InputParser(file);
        assertThrows(Exception.class, parser::parse);
    }

    // Test for invalid type for coordinates
    @Test
    public void testInvalidDatatypesInCoordinates() throws IOException {
        String file = "input/test_invalid_coordinates.txt";
        Files.writeString(Path.of(file), """
            Zeitraum:
            100 1
            Einfallspunkte:
            A x y B 5
            Kreuzungen:
            B 2 2 A 1
            """);

        InputParser parser = new InputParser(file);
        assertThrows(NumberFormatException.class, parser::parse);
    }
}
