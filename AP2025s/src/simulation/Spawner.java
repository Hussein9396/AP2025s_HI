package simulation;
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

    public boolean shouldSpawn(int currentTime) {
        if (currentTime == 0) return false;
        return currentTime % spawnInterval == 0;
    }

    public Vehicle spawnVehicle() {
        //test with constant speed
        //double speed = 45.0; // km/h
        double speed = randomNormal(45, 10);
        int id = ++vehicleCounter;   // jetzt als int
        return new Vehicle(id, speed, toFirstConnection);
    }

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
