import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PlanWriter {

    private Map<String, Point> points = new HashMap<>(); // alle Punkte (Einfallspunkte + Kreuzungen)
    private Set<String> connectionsSet = new HashSet<>(); // um doppelte Verbindungen zu verhindern
    private List<Connection> connections = new ArrayList<>(); // Liste aller Verbindungen

    public void parseInputFile(String filepath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filepath));
            boolean readingEinfall = false;
            boolean readingKreuzungen = false;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || line.equals("Zeitraum:")) continue;

                if (line.equals("Einfallspunkte:")) {
                    readingEinfall = true;
                    readingKreuzungen = false;
                    continue;
                }

                if (line.equals("Kreuzungen:")) {
                    readingEinfall = false;
                    readingKreuzungen = true;
                    continue;
                }

                if (readingEinfall) {
                    // Beispiel: A 0 0 B 2
                    String[] parts = line.split("\\s+");
                    String name = parts[0];
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    // Punkte direkt mit Namen speichern
                    points.put(name, new Point(name, x, y));
                }

                if (readingKreuzungen) {
                    // Beispiel: A 0 0 B 2 C 3
                    String[] parts = line.split("\\s+");
                    String name = parts[0];
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    points.put(name, new Point(name, x, y));

                    for (int i = 3; i < parts.length; i += 2) {
                        String target = parts[i];
                        addConnection(name, target);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addConnection(String from, String to) {
        String key1 = from + "-" + to;
        String key2 = to + "-" + from;

       if (!connectionsSet.contains(key1) && !connectionsSet.contains(key2) && points.containsKey(from) && points.containsKey(to)) {
            Point fromPoint = points.get(from);
            Point toPoint = points.get(to);
            if (fromPoint != null && toPoint != null) {
                connections.add(new Connection(from, to, fromPoint, toPoint));
                connectionsSet.add(key1);
            }
        }
    }

    public void writePlanFile(String outputPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (Connection c : connections) {
                writer.write(c.fromPoint.x + " " + c.fromPoint.y + " " +
                             c.toPoint.x + " " + c.toPoint.y);
                writer.newLine();
                writer.write(c.toPoint.x + " " + c.toPoint.y + " " +
                             c.fromPoint.x + " " + c.fromPoint.y);
                writer.newLine();
            }
            System.out.println("Plan.txt erfolgreich geschrieben.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
