package data;
public class Connection {
    private final String from;
    private final String to;
    private final Point fromPoint;
    private final Point toPoint;
    
    double distance;

    public double calculateDistance() {
        return Math.sqrt(Math.pow((toPoint.getX() - fromPoint.getX()), 2) + Math.pow((toPoint.getY() - fromPoint.getY()), 2));
    }

    public Connection(String from, String to, Point fromPoint, Point toPoint) {
        this.from = from;
        this.to = to;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.distance = calculateDistance();
    }
    public String getFrom() {
        return from;
    }
    public String getTo() {
        return to;
    }
    public Point getFromPoint() {
        return fromPoint;
    }
    public Point getToPoint() {
        return toPoint;
    }
    public double getDistance() {
        return distance;
    }
    
}
