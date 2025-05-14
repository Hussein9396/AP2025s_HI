package simulation;
import java.util.*;

import data.Connection;
import data.IntersectionData;
import io.FahrzeugeWriter;
import io.InputParser;
import io.PlanWriter;

public class Simulation {

    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Spawner> spawners = new ArrayList<>();
    private final Map<Connection, StatisticsEntry> statistics = new HashMap<>();
    private double timeStepInSeconds = 1.0;
    
    private final InputParser parser;
    private final PlanWriter planWriter;
    private final FahrzeugeWriter fahrzeugeWriter;

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
        String currentNode = v.getCurrentConnection().getTo();

        for (Spawner spawner : spawners) {
            if (spawner.getSpawnPoint().getName().equals(currentNode)) {
                return true;
            }
        }

        //can also be done with streams!
        IntersectionData intersection = null;
        for (IntersectionData k : parser.getIntersections()) {
            if (k.getName().equals(currentNode)) {
                intersection = k;
                break;
            }
        }

        if (intersection == null) return true;

        String originNode = v.getCurrentConnection().getFrom();
        Map<String, Integer> possibleTargets = new HashMap<>();
        for (Map.Entry<String, Integer> entry : intersection.getTargets().entrySet()) {
            if (!entry.getKey().equals(originNode)) {
                possibleTargets.put(entry.getKey(), entry.getValue());
            }
        }

        if (possibleTargets.isEmpty()) return true;

        int totalweight = possibleTargets.values().stream().mapToInt(Integer::intValue).sum();
        int randonValue = new Random().nextInt(totalweight) + 1;
        int comulativeweight = 0;
        String nextTarget = null;
        for (Map.Entry<String, Integer> entry : possibleTargets.entrySet()) {
            comulativeweight += entry.getValue();
            if (randonValue <= comulativeweight) {
                nextTarget = entry.getKey();
                break;
            }
        }

        String key = currentNode + "-" + nextTarget;
        Connection nextConnection = planWriter.getConnectionMap().get(key);

        if (nextConnection == null) return true;

        v.setCurrentConnection(nextConnection);
        v.setPositionOnConnection(0.0);
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
