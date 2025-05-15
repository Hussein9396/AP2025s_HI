package io;
import java.util.List;
import java.util.Map;

import data.EntryPoint;

import data.IntersectionPoint;
import data.Point;




import java.util.HashMap;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Paths;

// Parses the input file to extract entry points and intersection points and their properties.

public class InputParser {
    private final String filename;
    private final Map<String, Point> points = new HashMap<>();

    private int totalTimeSteps;
    private int timeStepInSeconds = 1;

    public InputParser(String filename) {
        this.filename = filename;
    }

    public void parse() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            boolean readingZeitraum = false;
            boolean readingEinfall = false;
            boolean readingKreuzungen = false;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                if (line.equals("Zeitraum:")) {
                    readingZeitraum = true;
                    readingEinfall = false;
                    readingKreuzungen = false;
                    continue;
                }
                if (line.equals("Einfallspunkte:")) {
                    readingZeitraum = false;
                    readingEinfall = true;
                    readingKreuzungen = false;
                    continue;
                }
                if (line.equals("Kreuzungen:")) {
                    readingZeitraum = false;
                    readingEinfall = false;
                    readingKreuzungen = true;
                    continue;
                }

                if (readingZeitraum) {
                    String[] parts = line.split("\\s+");
                    totalTimeSteps = Integer.parseInt(parts[0]);
                    timeStepInSeconds = Integer.parseInt(parts[1]);
                    readingZeitraum = false;
                    continue;
                }

                if (readingEinfall) {
                    String[] parts = line.split("\\s+");
                    String name = parts[0];
                    double x = Double.parseDouble(parts[1]) * 100;
                    double y = Double.parseDouble(parts[2]) * 100;
                    String target = parts[3];
                    int spawnInterval = Integer.parseInt(parts[4]);
                    points.put(name, new EntryPoint(name, x, y, target, spawnInterval));
                }

                if (readingKreuzungen) {
                    String[] parts = line.split("\\s+");
                    String name = parts[0];
                    double x = Double.parseDouble(parts[1]) * 100;
                    double y = Double.parseDouble(parts[2]) * 100;

                    Map<String, Integer> targets = new HashMap<>();
                    for (int i = 3; i < parts.length; i += 2) {
                        targets.put(parts[i], Integer.parseInt(parts[i + 1]));
                    }
                    points.put(name, new IntersectionPoint(name, x, y, targets));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Point> getPoints() {
        return points;
    }

    // Getter-Methoden für Zeitraum
    public int getTotalTimeSteps() {
        return totalTimeSteps;
    }

    public int getTimeStepInSeconds() {
        return timeStepInSeconds;
    }
}
