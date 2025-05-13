public class Connection {
    String from;
    String to;
    Point fromPoint;
    Point toPoint;
    
    double distance;

    public double calculateDistance() {
        return Math.sqrt(Math.pow((toPoint.x - fromPoint.x), 2) + Math.pow((toPoint.y - fromPoint.y), 2));
    }

    public Connection(String from, String to, Point fromPoint, Point toPoint) {
        this.from = from;
        this.to = to;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.distance = calculateDistance();
    }
    
}
