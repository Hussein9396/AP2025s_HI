package simulation;
import java.util.*;

import data.Connection;
import data.KreuzungData;
import io.FahrzeugeWriter;
import io.InputParser;
import io.PlanWriter;

public class Simulation {

    public List<Vehicle> vehicles = new ArrayList<>();
    public List<Spawner> spawners = new ArrayList<>();
    public Map<Connection, StatisticsEntry> statistics = new HashMap<>();
    public double timeStepInSeconds = 1.0;
    
    private InputParser parser;
    private PlanWriter planWriter;
    private FahrzeugeWriter fahrzeugeWriter;

    public Simulation(InputParser parser, PlanWriter planWriter, FahrzeugeWriter fahrzeugeWriter) {
        this.parser = parser;
        this.planWriter = planWriter;
        this.fahrzeugeWriter = fahrzeugeWriter;
    }

    public void run(int totalTimeSteps) {
        for (int t = 0; t < totalTimeSteps; t++) {
            for (Spawner spawner : spawners) {
                if (spawner.shouldSpawn(t)) {
                    vehicles.add(spawner.spawnVehicle());
                }
            }

            fahrzeugeWriter.writeSnapshot(t, vehicles);

            Iterator<Vehicle> iterator = vehicles.iterator();
            while (iterator.hasNext()) {
                Vehicle v = iterator.next();
                v.move(timeStepInSeconds);
                if (v.reachedEndOfConnection()) {
                    boolean removed = decideNextConnection(v);
                    if (removed) iterator.remove();
                }
            }

            updateStatistics();
        }

        System.out.println("Simulation abgeschlossen.");
    }

    private boolean decideNextConnection(Vehicle v) {
        String currentKnoten = v.currentConnection.getTo();

        for (Spawner spawner : spawners) {
            if (spawner.spawnPoint.getName().equals(currentKnoten)) {
                return true;
            }
        }

        //can also be done with streams!
        KreuzungData kreuzung = null;
        for (KreuzungData k : parser.getKreuzungen()) {
            if (k.getName().equals(currentKnoten)) {
                kreuzung = k;
                break;
            }
        }

        if (kreuzung == null) return true;

        String herkunft = v.currentConnection.getFrom();
        Map<String, Integer> möglicheZiele = new HashMap<>();
        for (Map.Entry<String, Integer> entry : kreuzung.getTargets().entrySet()) {
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
            Connection conn = v.getCurrentConnection();
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

    public List<Spawner> getSpawners() {
        return spawners;
    }
    
    public Map<Connection, StatisticsEntry> getStatistics() {
        return statistics;
    }
    
    public void addSpawner(Spawner spawner) {
        spawners.add(spawner);
    }

    public void addStatisticsEntry(Connection connection, StatisticsEntry entry) {
        statistics.put(connection, entry);
    }
}
