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
import java.io.*;

/**
 * Main class for the simulation application.
 */
public class App {
    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Fehler: Bitte geben Sie genau 3 Argumente an.");
            System.out.println("Verwendung: java App <Eingabe-Datei> <Output-Ordner> <TimeSteps>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFolder = args[1];
        String timeStepsArg = args[2];

        // Check if input file exists
        File input = new File(inputFile);
        if (!input.exists() || !input.isFile()) {
            System.out.println("Fehler: Eingabedatei '" + inputFile + "' existiert nicht.");
            System.exit(1);
        }

        // Check output folder name
        if (outputFolder.trim().isEmpty()) {
            System.out.println("Fehler: Der Ausgabepfad darf nicht leer sein.");
            System.exit(1);
        }

        // Parse time steps
        int totalTimeSteps;
        try {
            totalTimeSteps = Integer.parseInt(timeStepsArg);
            if (totalTimeSteps <= 0 || totalTimeSteps > 1800) {
                System.out.println("Fehler: TimeSteps muss eine positive ganze Zahl zwischen 1 und 1800 sein (entspricht halbe Stunde).");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Fehler: TimeSteps muss eine ganze Zahl sein.");
            System.exit(1);
            return;
        }

        File outputFile = new File(outputFolder);
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

        simulation.run(totalTimeSteps);

        fahrzeugeWriter.close();
        StatistikWriter statistikWriter = new StatistikWriter();
        statistikWriter.write(outputFolder + "/Statistik.txt", simulation.getStatistics());

        System.out.println("Programm abgeschlossen.");
    }
}