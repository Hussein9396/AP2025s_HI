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
/*
 * Fahrzeug-Simulator - Hauptklasse 
 */
public class App {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Aufruf: java App <Eingabe-Datei> <Output-Ordner>");
            return;
        }

        String inputFile = args[0];
        String outputFolder = args[1];

        java.io.File outputFile = new java.io.File(outputFolder);
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }

        InputParser parser = new InputParser(inputFile);
        parser.parse();

        PlanWriter planWriter = new PlanWriter(
                parser.getPoints(),
                parser.getIntersections(),
                parser.getSpawnerData()
        );
        planWriter.writePlanFile(outputFolder + "/Plan.txt");

        FahrzeugeWriter fahrzeugeWriter = new FahrzeugeWriter(outputFolder + "/Fahrzeuge.txt");
        Simulation simulation = new Simulation(parser, planWriter, fahrzeugeWriter);

        for (Connection c : planWriter.getConnections()) {
            simulation.addStatisticsEntry(c, new StatisticsEntry(c));
        }

        Map<String, Connection> connectionMap = planWriter.getConnectionMap();
        for (SpawnerData data : parser.getSpawnerData()) {
            Point startPoint = parser.getPoints().get(data.getName());
            String key = data.getName() + "-" + data.getTargetIntersecion();
            Connection firstConnection = connectionMap.get(key);

            if (firstConnection != null) {
                simulation.addSpawner(new Spawner(startPoint, firstConnection, data.getSpawnInterval()));
            } else {
                System.out.println("WARNUNG: Keine Verbindung für Spawner " + key);
            }
        }

        int totalTimeSteps = 100;
        simulation.run(totalTimeSteps);

        fahrzeugeWriter.close();
        StatistikWriter statistikWriter = new StatistikWriter();
        statistikWriter.write(outputFolder + "/Statistik.txt", simulation.getStatistics());

        System.out.println("Programm abgeschlossen.");
    }
}
