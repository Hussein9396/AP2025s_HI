package simulation;

import java.util.*;

import data.Connection;
import data.Point;
import data.IntersectionPoint;
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

    //Main Simulation Loop; advances time, spawns new vehicles, moves vehicles, and updates statistics.
    public void run(int totalTimeSteps) {
        //advance time
        for (int t = 0; t <= totalTimeSteps; t++) {
            // spawn new vehicles if it is an entry point
            for (Spawner spawner : spawners) {
                if (spawner.shouldSpawn(t)) {
                    vehicles.add(spawner.spawnVehicle());
                }
            }

            fahrzeugeWriter.writeFahrzeugeFile(t, vehicles);

            // move vehicles
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

        //System.out.println("Simulation abgeschlossen.");
    }

    //Decides the next connection for a vehicle. If target is an entry point (spawner), it removes the vehicle from the simulation.
    //true = remove vehicle, false = keep vehicle
    private boolean decideNextConnection(Vehicle v) {
        String currentNode = v.getCurrentConnection().getTo();

        for (Spawner spawner : spawners) {
            if (spawner.getSpawnPoint().getName().equals(currentNode)) {
                return true;
            }
        }

        Point p = parser.getPoints().get(currentNode);
        if (!(p instanceof IntersectionPoint intersection)) return true;

        String originNode = v.getCurrentConnection().getFrom();
        Map<String, Integer> possibleTargets = new HashMap<>();
        
        // Fills possibleTargets with all possible targets from the intersection, excluding the origin node
        for (Map.Entry<String, Integer> entry : intersection.getTargets().entrySet()) {
            if (!entry.getKey().equals(originNode)) {
                possibleTargets.put(entry.getKey(), entry.getValue());
            }
        }

        // If there are no possible targets, return true to remove the vehicle
        if (possibleTargets.isEmpty()) return true;

        int totalWeight = possibleTargets.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = new Random().nextInt(totalWeight) + 1;
        int cumulativeWeight = 0;
        String nextTarget = null;
        for (Map.Entry<String, Integer> entry : possibleTargets.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomValue <= cumulativeWeight) {
                nextTarget = entry.getKey();
                break;
            }
        }

        String key = currentNode + "-" + nextTarget;
        Connection nextConnection = planWriter.getConnectionMap().get(key);

        if (nextConnection == null) return true;

        // Calculate leftover distance (overshoot) and transfer it to the next connection
        double leftover = v.getPositionOnConnection() - 1.0;
        v.setCurrentConnection(nextConnection);
        v.setPositionOnConnection(leftover);
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