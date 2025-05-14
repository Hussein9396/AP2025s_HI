public class SpawnerData {
    String name;
    String zielKreuzung;
    int takt;

    public SpawnerData(String name, String zielKreuzung, int takt) {
        this.name = name;
        this.zielKreuzung = zielKreuzung;
        this.takt = takt;
    }
    public String getName() {
        return name;
    }
    public String getZielKreuzung() {
        return zielKreuzung;
    }
    public int getTakt() {
        return takt;
    }
}
