package data;

public class EntryPoint extends Point {
    private final String targetIntersection;
    private final int spawnInterval;

    public EntryPoint(String name, double x, double y, String targetIntersection, int spawnInterval) {
        super(name, x, y);
        this.targetIntersection = targetIntersection;
        this.spawnInterval = spawnInterval;
    }

    public String getTargetIntersection() {
        return targetIntersection;
    }

    public int getSpawnInterval() {
        return spawnInterval;
    }

    @Override
    public String getType() {
        return "EntryPoint";
    }
}
