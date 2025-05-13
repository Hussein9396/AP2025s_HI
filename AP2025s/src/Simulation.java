import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Simulation {

    public List<Vehicle> vehicles = new ArrayList<>();
    public List<Spawner> spawners = new ArrayList<>();
    public Map<Connection, StatisticsEntry> statistics = new HashMap<>();
    public double timeStepInSeconds = 1.0;
    private InputParser parser;
    private PlanWriter planWriter;
    private BufferedWriter fahrzeugeWriter;

    public Simulation(InputParser parser, PlanWriter planWriter) {
        this.parser = parser;
        this.planWriter = planWriter;
    }

    public void run(int totalTimeSteps) {
        for (int t = 0; t < totalTimeSteps; t++) {
            System.out.println("\n=== Zeitschritt " + t + " ===");

            // 1️⃣ Fahrzeuge erzeugen
            for (Spawner spawner : spawners) {
                if (spawner.shouldSpawn(t)) {
                    Vehicle newVehicle = spawner.spawnVehicle();
                    vehicles.add(newVehicle);
                    System.out.println("NEUES Fahrzeug erzeugt: ID " + newVehicle.id +
                            " auf " + newVehicle.currentConnection.from + " → " +
                            newVehicle.currentConnection.to);
                }
            }
            writeFahrzeugeSnapshot(t);

            // 2️⃣ Fahrzeuge bewegen
            Iterator<Vehicle> iterator = vehicles.iterator();
            while (iterator.hasNext()) {
                Vehicle vehicle = iterator.next();
                vehicle.move(timeStepInSeconds);

                if (vehicle.reachedEndOfConnection()) {
                    boolean removed = decideNextConnection(vehicle);
                    if (removed) {
                        System.out.println("Fahrzeug " + vehicle.id + " hat Ziel erreicht und wird entfernt.");
                        iterator.remove();
                    }
                }
            }

            // 3️⃣ Statistik aktualisieren
            updateStatistics();

        }
    }

    private boolean decideNextConnection(Vehicle v) {
        String currentKnoten = v.currentConnection.to;

        for (Spawner spawner : spawners) {
            if (spawner.spawnPoint.name.equals(currentKnoten)) {
                return true;
            }
        }

        KreuzungData kreuzung = null;
        for (KreuzungData k : parser.getKreuzungen()) {
            if (k.name.equals(currentKnoten)) {
                kreuzung = k;
                break;
            }
        }

        if (kreuzung == null) return true;

        String herkunft = v.currentConnection.from;
        Map<String, Integer> möglicheZiele = new HashMap<>();
        for (Map.Entry<String, Integer> entry : kreuzung.targets.entrySet()) {
            if (!entry.getKey().equals(herkunft)) {
                möglicheZiele.put(entry.getKey(), entry.getValue());
            }
        }

        if (möglicheZiele.isEmpty()) return true;

        int summe = möglicheZiele.values().stream().mapToInt(Integer::intValue).sum();
        int zufall = new Random().nextInt(summe) + 1;
        int laufend = 0;
        String nächstesZiel = null;
        for (Map.Entry<String, Integer> entry : möglicheZiele.entrySet()) {
            laufend += entry.getValue();
            if (zufall <= laufend) {
                nächstesZiel = entry.getKey();
                break;
            }
        }

        String key = currentKnoten + "-" + nächstesZiel;
        Connection nextConnection = planWriter.getConnectionMap().get(key);

        if (nextConnection == null) return true;

        v.currentConnection = nextConnection;
        v.positionOnConnection = 0.0;
        return false;
    }

    private void updateStatistics() {
        Map<Connection, Integer> vehicleCounts = new HashMap<>();
        for (Vehicle v : vehicles) {
            Connection conn = v.currentConnection;
            vehicleCounts.put(conn, vehicleCounts.getOrDefault(conn, 0) + 1);
        }

        for (Map.Entry<Connection, Integer> entry : vehicleCounts.entrySet()) {
            StatisticsEntry stat = statistics.get(entry.getKey());
            if (stat != null) {
                for (int i = 0; i < entry.getValue(); i++) {
                    stat.vehicleOnStep();
                }
            }
        }

        for (StatisticsEntry stat : statistics.values()) {
            stat.finalizeStep();
        }
    }

    public void writeStatisticsFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Gesamtanzahl Fahrzeuge pro 100 m:\n");
            for (Map.Entry<Connection, StatisticsEntry> entry : statistics.entrySet()) {
                Connection c = entry.getKey();
                StatisticsEntry stats = entry.getValue();
                writer.write(c.from + " -> " + c.to + ": " + stats.getTotalPer100m() + "\n");
            }

            writer.write("\nMaximale Anzahl Fahrzeuge pro 100 m:\n");
            for (Map.Entry<Connection, StatisticsEntry> entry : statistics.entrySet()) {
                Connection c = entry.getKey();
                StatisticsEntry stats = entry.getValue();
                writer.write(c.from + " -> " + c.to + ": " + stats.getMaxPer100m() + "\n");
            }

            System.out.println("Statistik.txt erfolgreich geschrieben.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareFahrzeugeOutput(String filename) {
        try {
            fahrzeugeWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFahrzeugeSnapshot(int t) {
        try {
            fahrzeugeWriter.write("*** t = " + t + "\n");
            for (Vehicle v : vehicles) {
                Point from = v.currentConnection.fromPoint;
                Point to = v.currentConnection.toPoint;
                double x = from.x + (to.x - from.x) * v.positionOnConnection;
                double y = from.y + (to.y - from.y) * v.positionOnConnection;

                fahrzeugeWriter.write(x/100 + " " + y/100 + " " + to.x/100 + " " + to.y/100 + " " + v.id + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeFahrzeugeOutput() {
        try {
            if (fahrzeugeWriter != null) {
                fahrzeugeWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
