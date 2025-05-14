import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StatistikWriter {

    public void write(String filename, Map<Connection, StatisticsEntry> statistics) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Gesamtanzahl Fahrzeuge pro 100 m:\n");
            for (Map.Entry<Connection, StatisticsEntry> entry : statistics.entrySet()) {
                Connection c = entry.getKey();
                StatisticsEntry stats = entry.getValue();
                writer.write(c.getFrom() + " -> " + c.getTo() + ": " + stats.getTotalPer100m() + "\n");
            }

            writer.write("\nMaximale Anzahl Fahrzeuge pro 100 m:\n");
            for (Map.Entry<Connection, StatisticsEntry> entry : statistics.entrySet()) {
                Connection c = entry.getKey();
                StatisticsEntry stats = entry.getValue();
                writer.write(c.getFrom() + " -> " + c.getTo() + ": " + stats.getMaxPer100m() + "\n");
            }

            System.out.println("Statistik.txt erfolgreich geschrieben.");
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Schreiben der Statistik", e);
        }
    }
}
