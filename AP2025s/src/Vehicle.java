class Vehicle {
    int id;                            // ID des Fahrzeugs
    double speed;                       // in km/h → umrechnen in m/s
    Connection currentConnection;
    double positionOnConnection;        // 0.0 bis 1.0

    void move(double timeStepInSeconds) {
        double streetLength = currentConnection.distance;    // z.B. in Metern
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

}
