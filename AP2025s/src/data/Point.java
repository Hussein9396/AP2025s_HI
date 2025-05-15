package data;

public abstract class Point {
    private final String name;
    private final double x;
    private final double y;

    public Point(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // ✅ Abstract method for type
    public abstract String getType();
}
