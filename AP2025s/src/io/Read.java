package io;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Read {

    private String inputFilePath;
    private String outputFolderPath;
    private String outputFileName;

    public Read(String inputFilePath, String outputFolderPath, String outputFileName) {
        this.inputFilePath = inputFilePath;
        this.outputFolderPath = outputFolderPath;
        this.outputFileName = outputFileName;
    }

    public void readAndWrite() {
        try {
            // Inhalt der Eingabedatei lesen
            String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));

            // Ordner erstellen, falls nicht vorhanden
            File outputFolder = new File(outputFolderPath);
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            // Neue Datei im Zielordner schreiben
            FileWriter writer = new FileWriter(new File(outputFolderPath, outputFileName));
            writer.write(content);
            writer.close();

            System.out.println("Datei erfolgreich nach " + outputFolderPath + "/" + outputFileName + " kopiert.");

        } catch (IOException e) {
            System.out.println("Fehler beim Lesen oder Schreiben der Datei: " + e.getMessage());
            e.printStackTrace();
            
        }
    }

}
