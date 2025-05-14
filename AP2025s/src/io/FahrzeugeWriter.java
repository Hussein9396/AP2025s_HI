package io;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import data.Point;
import simulation.Vehicle;

public class FahrzeugeWriter {
    private BufferedWriter writer;

    public FahrzeugeWriter(String filename) {
        try {
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Öffnen von Fahrzeuge.txt", e);
        }
    }

    public void writeSnapshot(int t, List<Vehicle> vehicles) {
        try {
            writer.write("*** t = " + t + "\n");
            for (Vehicle v : vehicles) {
                Point from = v.getCurrentConnection().getFromPoint();
                Point to = v.getCurrentConnection().getToPoint();
                double x = from.getX() + (to.getX() - from.getX()) * v.getPositionOnConnection();
                double y = from.getY() + (to.getY() - from.getY()) * v.getPositionOnConnection();
                writer.write(x / 100 + " " + y / 100 + " " + to.getX() / 100 + " " + to.getY() / 100 + " " + v.getId() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Schreiben in Fahrzeuge.txt", e);
        }
    }

    public void close() {
        try {
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
