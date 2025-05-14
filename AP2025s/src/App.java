import java.util.Map;

public class App {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Aufruf: java App <Eingabe-Datei> <Output-Ordner>");
            return;
        }

        String eingabeDatei = args[0];
        String outputFolder = args[1];

        java.io.File outputFile = new java.io.File(outputFolder);
        
        if(!outputFile.exists()) {
            outputFile.mkdirs();
        }

        // 1️⃣ Eingabe einlesen
        InputParser parser = new InputParser(eingabeDatei);
        parser.parse();

        // 2️⃣ PlanWriter erzeugen + Verbindungen erstellen
        PlanWriter planWriter = new PlanWriter(
                parser.getPoints(),
                parser.getKreuzungen(),
                parser.getSpawnerData()
        );

        // ➕ optional: Plan.txt schreiben
        planWriter.writePlanFile(outputFolder + "/Plan.txt");

        // 3️⃣ Simulation vorbereiten
        Simulation simulation = new Simulation(parser, planWriter);

        // ➕ Statistik vorbereiten: alle Connections
        for (Connection c : planWriter.getConnections()) {
            simulation.statistics.put(c, new StatisticsEntry(c));
        }

        // ➕ Spawner vorbereiten → NEU: connectionMap nutzen
        Map<String, Connection> connectionMap = planWriter.getConnectionMap();

        for (SpawnerData data : parser.getSpawnerData()) {
            Point startPoint = parser.getPoints().get(data.name);
            String key = data.name + "-" + data.zielKreuzung;
            Connection firstConnection = connectionMap.get(key);

            if (firstConnection != null) {
                simulation.spawners.add(new Spawner(startPoint, firstConnection, data.takt));
            } else {
                System.out.println("WARNUNG: Keine Verbindung für Spawner " + key);
            }
        }

        // 4️⃣ Fahrzeuge.txt vorbereiten
        simulation.prepareFahrzeugeOutput(outputFolder + "/Fahrzeuge.txt");

        // 5️⃣ Simulation starten
        int totalTimeSteps = 50;   // Beispiel: 1000 Zeitschritte
        simulation.run(totalTimeSteps);

        // 6️⃣ Fahrzeuge.txt schließen
        simulation.closeFahrzeugeOutput();

        // 7️⃣ Statistik schreiben
        simulation.writeStatisticsFile(outputFolder + "/Statistik.txt");

        System.out.println("Simulation abgeschlossen.");
    }
}
