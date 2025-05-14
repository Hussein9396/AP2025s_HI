package data;
import java.util.Map;

public class KreuzungData {
    String name;
    Map<String, Integer> targets;

    public KreuzungData(String name, Map<String, Integer> targets) {
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