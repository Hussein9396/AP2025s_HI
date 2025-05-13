import java.util.Map;

public class App {
    public static void main(String[] args) {
        // 1️⃣ Eingabe einlesen
        InputParser parser = new InputParser("IHK_Download/IHK_01/Eingabe.txt");
        parser.parse();

        // 2️⃣ PlanWriter erzeugen + Verbindungen erstellen
        PlanWriter planWriter = new PlanWriter(
                parser.getPoints(),
                parser.getKreuzungen(),
                parser.getSpawnerData()
        );

        // ➕ optional: Plan.txt schreiben
        //planWriter.writePlanFile("output/Plan.txt");

        // 3️⃣ Simulation vorbereiten
        Simulation simulation = new Simulation();

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

        // 4️⃣ Simulation starten
        int totalTimeSteps = 50;   // Beispiel: 50 Zeitschritte
        simulation.run(totalTimeSteps);

        // 5️⃣ Statistik schreiben
        simulation.writeStatisticsFile("output/Statistik_neu_neu.txt");

        System.out.println("Simulation abgeschlossen.");
    }
}
