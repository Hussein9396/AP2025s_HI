package data;

import java.util.Map;

public class IntersectionPoint extends Point {
    private final Map<String, Integer> targets;

    public IntersectionPoint(String name, double x, double y, Map<String, Integer> targets) {
        super(name, x, y);
        this.targets = targets;
    }

    public Map<String, Integer> getTargets() {
        return targets;
    }

    @Override
    public String getType() {
        return "IntersectionPoint";
    }
}
