package logic;
import java.util.Random;

import data.Connection;
import data.Point;

public class Spawner {
    private static int vehicleCounter = 0;
    private final Point spawnPoint;
    private final Connection toFirstConnection;
    private final int spawnInterval;

    public Spawner(Point spawnPoint, Connection toFirstConnection, int spawnInterval) {
        this.spawnPoint = spawnPoint;
        this.toFirstConnection = toFirstConnection;
        this.spawnInterval = spawnInterval;
    }

    // Determines if a vehicle should be spawned at the current time step.
    // true = spawn vehicle, false = do not spawn vehicle
    public boolean shouldSpawn(int currentTime) {
        // At the beginning of the simulation, no vehicles are spawned.
        // Then vehicles are spawned every spawnInterval time step.
        if (currentTime == 0) return false;
        return currentTime % spawnInterval == 0;
    }

    // Spawns a new vehicle with a random speed.
    // The speed is normally distributed with a mean of 45 km/h and a standard deviation of 10 km/h.
    public Vehicle spawnVehicle() {
        //test with constant speed
        //double speed = 45.0; // km/h
        double speed = randomNormal(45, 10);
        int id = ++vehicleCounter;
        return new Vehicle(id, speed, toFirstConnection);
    }

    // Generates a random number from a normal distribution with the given mean and standard deviation.
    private double randomNormal(double mean, double stdDev) {
        Random rand = new Random();
        return mean + stdDev * rand.nextGaussian();
    }

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public Connection getToFirstConnection() {
        return toFirstConnection;
    }

    public int getspawnInterval() {
        return spawnInterval;
    }
}
