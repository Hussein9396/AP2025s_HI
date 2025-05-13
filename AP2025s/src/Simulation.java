import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Simulation {

    public List<Vehicle> vehicles = new ArrayList<>();
    public List<Spawner> spawners = new ArrayList<>();
    public Map<Connection, StatisticsEntry> statistics = new HashMap<>();
    public double timeStepInSeconds = 1.0;

    /**
     * Hauptmethode: steuert die Simulation
     */
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

            // 📝 Diagnose: aktueller Fahrzeugbestand
            System.out.println("Anzahl Fahrzeuge aktuell: " + vehicles.size());
            for (Vehicle v : vehicles) {
                System.out.println("Vehicle " + v.id + " auf " + v.currentConnection.from +
                                " → " + v.currentConnection.to + " | Position: " +
                                String.format("%.4f", v.positionOnConnection));
            }

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


    /**
     * Dummy-Methode: aktuell verschwindet jedes Fahrzeug am Ende
     * Später kannst du hier deine Routing-Logik einbauen
     */
    private boolean decideNextConnection(Vehicle vehicle) {
        // Aktuell: jedes Fahrzeug verschwindet wenn Connection zu Ende
        return true;
    }

    /**
     * Beispiel: Statistikdaten aktualisieren
     */
    private void updateStatistics() {
        // Beispiel-Logik: zähle Fahrzeuge pro Connection
        Map<Connection, Integer> vehicleCounts = new HashMap<>();

        for (Vehicle v : vehicles) {
            Connection conn = v.currentConnection;
            vehicleCounts.put(conn, vehicleCounts.getOrDefault(conn, 0) + 1);
        }

        // Trage gezählte Daten in StatisticsEntry ein
        for (Map.Entry<Connection, Integer> entry : vehicleCounts.entrySet()) {
            StatisticsEntry stat = statistics.get(entry.getKey());
            if (stat != null) {
                for (int i = 0; i < entry.getValue(); i++) {
                    stat.vehicleOnStep();
                }
            }
        }

        // Max-Werte aktualisieren
        for (StatisticsEntry stat : statistics.values()) {
            stat.finalizeStep();
        }
    }

    /**
     * Statistik nach Ende der Simulation ausgeben
     */
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
}
