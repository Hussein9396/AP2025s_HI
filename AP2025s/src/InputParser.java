import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Paths;

public class InputParser {
    private String filename;

    private Map<String, Point> points = new HashMap<>();
    private List<SpawnerData> spawnerData = new ArrayList<>();
    private List<KreuzungData> kreuzungen = new ArrayList<>();

    public InputParser(String filename) {
        this.filename = filename;
    }

    public void parse() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            boolean readingEinfall = false;
            boolean readingKreuzungen = false;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || line.equals("Zeitraum:")) continue;
                if (line.equals("Einfallspunkte:")) { readingEinfall = true; readingKreuzungen = false; continue; }
                if (line.equals("Kreuzungen:")) { readingEinfall = false; readingKreuzungen = true; continue; }

                if (readingEinfall) {
                    String[] parts = line.split("\\s+");
                    String name = parts[0];
                    double x = Double.parseDouble(parts[1]) * 100;
                    double y = Double.parseDouble(parts[2]) * 100;
                    String ziel = parts[3];
                    int takt = Integer.parseInt(parts[4]);
                    points.put(name, new Point(name, x, y));
                    spawnerData.add(new SpawnerData(name, ziel, takt));
                }

                if (readingKreuzungen) {
                    String[] parts = line.split("\\s+");
                    String name = parts[0];
                    double x = Double.parseDouble(parts[1]) * 100;
                    double y = Double.parseDouble(parts[2]) * 100;
                    points.put(name, new Point(name, x, y));

                    Map<String, Integer> targets = new HashMap<>();
                    for (int i = 3; i < parts.length; i += 2) {
                        targets.put(parts[i], Integer.parseInt(parts[i+1]));
                    }
                    kreuzungen.add(new KreuzungData(name, targets));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Point> getPoints() { return points; }

    public List<SpawnerData> getSpawnerData() { return spawnerData; }

    public List<KreuzungData> getKreuzungen() { return kreuzungen; }
}
