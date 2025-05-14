package simulation;
import data.Connection;

public class Vehicle {
    private final int id;                            // ID des Fahrzeugs
    private final double speed;                       // in km/h → umrechnen in m/s
    private Connection currentConnection;
    private double positionOnConnection;        // 0.0 bis 1.0

    void move(double timeStepInSeconds) {
        double streetLength = currentConnection.getDistance();    // z.B. in Metern
        double speedInMS = speed / 3.6;                     // km/h → m/s
        double distance = speedInMS * timeStepInSeconds;
        double progress = distance / streetLength;

        positionOnConnection += progress;
    }

    boolean reachedEndOfConnection() {
        return positionOnConnection >= 1.0;
    }
    
    Vehicle(int id, double speed, Connection currentConnection) {
        this.id = id;
        this.speed = speed;
        this.currentConnection = currentConnection;
        this.positionOnConnection = 0.0;
    }
    // Getter-Methoden
    public int getId() {
        return id;
    }
    public double getSpeed() {
        return speed;
    }
    public Connection getCurrentConnection() {
        return currentConnection;
    }
    public double getPositionOnConnection() {
        return positionOnConnection;
    }
    public void setCurrentConnection(Connection currentConnection) {
        this.currentConnection = currentConnection;
    }
    public void setPositionOnConnection(double positionOnConnection) {
        this.positionOnConnection = positionOnConnection;
    }

}
