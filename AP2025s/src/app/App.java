package app;

import java.util.Map;

import data.Connection;
import data.EntryPoint;
import data.Point;
import io.FahrzeugeWriter;
import io.InputParser;
import io.PlanWriter;
import io.StatistikWriter;
import simulation.Simulation;
import simulation.Spawner;
import simulation.StatisticsEntry;

/**
 * Main class for the simulation application.
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
                parser.getPoints()
        );
        planWriter.writePlanFile(outputFolder + "/Plan.txt");

        FahrzeugeWriter fahrzeugeWriter = new FahrzeugeWriter(outputFolder + "/Fahrzeuge.txt");
        Simulation simulation = new Simulation(parser, planWriter, fahrzeugeWriter);

        // Add statistics tracking for each connection
        for (Connection c : planWriter.getConnections()) {
            simulation.addStatisticsEntry(c, new StatisticsEntry(c));
        }

        // Add spawners from EntryPoints
        Map<String, Connection> connectionMap = planWriter.getConnectionMap();
        for (Point point : parser.getPoints().values()) {
            if (point instanceof EntryPoint ep) {
                String key = ep.getName() + "-" + ep.getTargetIntersection();
                Connection firstConnection = connectionMap.get(key);

                if (firstConnection != null) {
                    simulation.addSpawner(new Spawner(ep, firstConnection, ep.getSpawnInterval()));
                } else {
                    System.out.println("WARNUNG: Keine Verbindung für Spawner " + key);
                }
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