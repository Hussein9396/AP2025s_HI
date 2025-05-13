import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PlanWriter {

    private Map<String, Point> points = new HashMap<>();
    private Set<String> connectionsSet = new HashSet<>();
    private List<Connection> connections = new ArrayList<>();
    private Map<String, Connection> connectionMap = new HashMap<>();   // ✅ NEU für Referenz-Sicherheit


    /**
     * Konstruktor → übernimmt Daten vom InputParser
     */
    public PlanWriter(Map<String, Point> points, List<KreuzungData> kreuzungen, List<SpawnerData> spawners) {
        this.points = points;
        buildConnections(kreuzungen);
        addSpawnerConnections(spawners);   // ➕ EP → Kreuzung Verbindungen
    }

    /**
     * Verbindungen Kreuzung → Kreuzung
     */
    private void buildConnections(List<KreuzungData> kreuzungen) {
        for (KreuzungData kd : kreuzungen) {
            String from = kd.name;
            if (!points.containsKey(from)) continue;

            for (String target : kd.targets.keySet()) {
                addConnection(from, target);
            }
        }
    }

    /**
     * Verbindungen Einfallspunkt → Kreuzung
     */
    private void addSpawnerConnections(List<SpawnerData> spawners) {
        for (SpawnerData spawner : spawners) {
            addConnection(spawner.name, spawner.zielKreuzung);
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
                writer.write(c.fromPoint.x/100 + " " + c.fromPoint.y/100 + " " +
                             c.toPoint.x/100 + " " + c.toPoint.y/100);
                writer.newLine();

                writer.write(c.toPoint.x/100 + " " + c.toPoint.y/100 + " " +
                             c.fromPoint.x/100 + " " + c.fromPoint.y/100);
                writer.newLine();
            }
            System.out.println("Plan.txt erfolgreich geschrieben.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
