package data;
import java.util.Map;

public class IntersectionData {
    private final String name;
    private final Map<String, Integer> targets;

    public IntersectionData(String name, Map<String, Integer> targets) {
        this.name = name;
        this.targets = targets;
    }
    
    public String getName() {
        return name;
    }
    
    public Map<String, Integer> getTargets() {
        return targets;
    }

}