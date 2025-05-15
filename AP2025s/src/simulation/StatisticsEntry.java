package simulation;
import data.Connection;

public class StatisticsEntry {
    private final double length;          
    private int totalVehicles = 0;              
    private int currentVehiclesInStep = 0;      
    private int maxVehiclesInStep = 0;          

    public StatisticsEntry(Connection connection) {
        this.length = connection.getDistance();
    }

    public void vehicleOnStep() {
        currentVehiclesInStep++;
    }

    public void finalizeStep() {
        totalVehicles += currentVehiclesInStep;
        if (currentVehiclesInStep > maxVehiclesInStep) {
            maxVehiclesInStep = currentVehiclesInStep;
        }
        currentVehiclesInStep = 0;
    }

    public double getTotalPer100m() {
        return totalVehicles / (length / 100.0);
    }

    public double getMaxPer100m() {
        return maxVehiclesInStep / (length / 100.0);
    }
    
}
