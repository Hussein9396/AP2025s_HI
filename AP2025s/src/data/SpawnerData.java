package data;
public class SpawnerData {
    String name;
    String targetIntersection;
    int spawnInterval;

    public SpawnerData(String name, String targetIntersection, int spawnInterval) {
        this.name = name;
        this.targetIntersection = targetIntersection;
        this.spawnInterval = spawnInterval;
    }
    public String getName() {
        return name;
    }
    public String getTargetIntersecion() {
        return targetIntersection;
    }
    public int getSpawnInterval() {
        return spawnInterval;
   }
}
