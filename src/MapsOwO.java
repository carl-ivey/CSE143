import java.util.*;
public class MapsOwO
{
    public static void main(String[] args)
    {
        Map<String, Set<String>> yif = new HashMap<>();
        Set<String> f1 = new HashSet<>();
        f1.add("x");
        f1.add("y");
        f1.add("z");
        Set<String> f2 = new HashSet<>();
        f2.add("i");
        f2.add("j");
        yif.put("A", f1);
        yif.put("B", f2);
        System.out.printf("%.3f\n", computeAverage(yif));
    }
    
    public static double computeAverage(Map<String, Set<String>> friends)
    {
        double total = 0.0;
        
        for (String friend : friends.keySet())
        {
            total += friends.get(friend).size();
        }
        
        return total / friends.size();
    }
}
