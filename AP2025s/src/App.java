public class App {
    public static void main(String[] args) {
        // relativer Pfad von deinem Hauptordner aus
        String inputPath = "Eingabe.txt";
        String outputFolder = "output";
        String outputFile = "kopie.txt";

        Read reader = new Read(inputPath, outputFolder, outputFile);
        reader.readAndWrite();
    }
}
