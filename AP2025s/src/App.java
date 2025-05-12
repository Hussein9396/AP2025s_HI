public class App {
    public static void main(String[] args) {
        Read reader = new Read("eingabe.txt", "output", "kopie.txt");
        reader.readAndWrite();
    }
}
