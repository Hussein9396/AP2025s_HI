public class App {
    public static void main(String[] args) {
        // relativer Pfad von deinem Hauptordner aus
        String inputPath = "IHK_Download/IHK_01/Eingabe.txt";
        String outputFolder = "output";
        String outputFile = "Plan.txt";

        //Read reader = new Read(inputPath, outputFolder, outputFile);
        //reader.readAndWrite();
        PlanWriter planWriter = new PlanWriter();
        planWriter.parseInputFile(inputPath);
        planWriter.writePlanFile(outputFolder + "/" + outputFile);
    }
}
