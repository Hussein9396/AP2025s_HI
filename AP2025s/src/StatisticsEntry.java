public class StatisticsEntry {
    Connection connection;
    double length;                      // Länge der Straße in Meter
    int totalVehicles = 0;              // Summe aller Fahrzeuge
    int currentVehiclesInStep = 0;      // Fahrzeuge in aktuellem Zeitschritt
    int maxVehiclesInStep = 0;          // Maximal gleichzeitig Fahrzeuge auf der Straße

    public StatisticsEntry(Connection connection) {
        this.connection = connection;
        this.length = connection.distance;
    }

    // Aufruf jedes Mal, wenn ein Fahrzeug auf diesem Abschnitt fährt
    public void vehicleOnStep() {
        currentVehiclesInStep++;
    }

    // Am Ende eines Zeitschritts wird das Maximum geprüft & current zurückgesetzt
    public void finalizeStep() {
        totalVehicles += currentVehiclesInStep;
        if (currentVehiclesInStep > maxVehiclesInStep) {
            maxVehiclesInStep = currentVehiclesInStep;
        }
        currentVehiclesInStep = 0;
    }

    // Für Ausgabe normierter Werte
    public double getTotalPer100m() {
        return totalVehicles / (length / 100.0);
    }

    public double getMaxPer100m() {
        return maxVehiclesInStep / (length / 100.0);
    }
    
}
