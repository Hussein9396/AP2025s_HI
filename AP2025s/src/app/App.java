package app;
import java.util.Map;

import data.Connection;
import data.Point;
import data.SpawnerData;
import io.FahrzeugeWriter;
import io.InputParser;
import io.PlanWriter;
import io.StatistikWriter;
import simulation.Simulation;
import simulation.Spawner;
import simulation.StatisticsEntry;
/**
 * ==========================================
 * Traffic Simulation Program - Main Workflow
 * ==========================================
 *
 * 1️⃣ Read and parse the input
 *    - InputParser reads road points, intersections, and spawner definitions from Eingabe.txt.
 *
 * 2️⃣ Build the road network (static map)
 *    - PlanWriter creates all road connections between points.
 *    - The network is fixed and does not change during the simulation.
 *
 * 3️⃣ Set up the simulation environment
 *    - Simulation prepares spawners and initializes statistics tracking.
 *
 * 4️⃣ Run the simulation
 *    - For each time step:
 *         → Vehicles are spawned (if their spawner is scheduled).
 *         → Vehicles move along the network.
 *         → Live vehicle positions are recorded in Fahrzeuge.txt (snapshot of current state).
 *         → Statistics counters are updated.
 *
 * 5️⃣ Write the final statistics
 *    - After simulation ends, StatistikWriter generates Statistik.txt from collected data.
 *
 * 6️⃣ Print program completion
 *    - Output "Programm abgeschlossen." to signal normal termination.
 *
 * Notes:
 * - The road network (Plan.txt) is written before the simulation starts.
 * - Fahrzeuge.txt is written incrementally during simulation (live data logging).
 * - This structure follows clean separation of concerns:
 *      → Input parsing, simulation logic, and file output are fully decoupled.
 */
public class App {
    public static void main(String[] args) {

        // Argument check
        if (args.length < 2) {
            System.out.println("Aufruf: java App <Eingabe-Datei> <Output-Ordner>");
            return;
        }

        String eingabeDatei = args[0];
        String outputFolder = args[1];

        // Create output folder if not existing
        java.io.File outputFile = new java.io.File(outputFolder);
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }

        // 1️⃣ Read and parse input
        InputParser parser = new InputParser(eingabeDatei);
        parser.parse();

        // 2️⃣ Build static road network
        PlanWriter planWriter = new PlanWriter(
                parser.getPoints(),
                parser.getKreuzungen(),
                parser.getSpawnerData()
        );
        planWriter.writePlanFile(outputFolder + "/Plan.txt");

        // 3️⃣ Prepare simulation environment
        FahrzeugeWriter fahrzeugeWriter = new FahrzeugeWriter(outputFolder + "/Fahrzeuge.txt");
        Simulation simulation = new Simulation(parser, planWriter, fahrzeugeWriter);

        // Add statistics tracking for each connection
        for (Connection c : planWriter.getConnections()) {
            simulation.addStatisticsEntry(c, new StatisticsEntry(c));
        }

        // Add spawners from input data
        Map<String, Connection> connectionMap = planWriter.getConnectionMap();
        for (SpawnerData data : parser.getSpawnerData()) {
            Point startPoint = parser.getPoints().get(data.getName());
            String key = data.getName() + "-" + data.getZielKreuzung();
            Connection firstConnection = connectionMap.get(key);

            if (firstConnection != null) {
                simulation.addSpawner(new Spawner(startPoint, firstConnection, data.getTakt()));
            } else {
                System.out.println("WARNUNG: Keine Verbindung für Spawner " + key);
            }
        }

        // 4️⃣ Run simulation
        int totalTimeSteps = 50;   // Example: 50 time steps
        simulation.run(totalTimeSteps);

        // 5️⃣ Write final statistics
        fahrzeugeWriter.close();
        StatistikWriter statistikWriter = new StatistikWriter();
        statistikWriter.write(outputFolder + "/Statistik.txt", simulation.getStatistics());

        // 6️⃣ Program complete
        System.out.println("Programm abgeschlossen.");
    }
}
