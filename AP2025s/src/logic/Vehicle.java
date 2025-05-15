package logic;
import data.Connection;

// This class represents a vehicle in the simulation.
// It contains information about the vehicle's ID, speed, current connection, and position on that connection.

public class Vehicle {
    private final int id;                           
    private final double speed;                      
    private Connection currentConnection;
    private double positionOnConnection;        

    void move(double timeStepInSeconds) {
        double streetLength = currentConnection.getDistance();
        double speedInMS = speed / 3.6;                     
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
