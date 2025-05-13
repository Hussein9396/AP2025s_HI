import java.util.Random;

class Spawner {
    private static int vehicleCounter = 0;
    Point spawnPoint;
    Connection toFirstConnection;
    int takt;

    public Spawner(Point spawnPoint, Connection toFirstConnection, int takt) {
        this.spawnPoint = spawnPoint;
        this.toFirstConnection = toFirstConnection;
        this.takt = takt;
    }

    public boolean shouldSpawn(int currentTime) {
        return currentTime % takt == 0;
    }

    public Vehicle spawnVehicle() {
        double speed = randomNormal(45, 10);
        int id = ++vehicleCounter;   // jetzt als int
        return new Vehicle(id, speed, toFirstConnection);
    }

    private double randomNormal(double mean, double stdDev) {
        Random rand = new Random();
        return mean + stdDev * rand.nextGaussian();
    }
}
