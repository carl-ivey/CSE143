import java.util.ArrayList;
import java.util.List;

public class AssassinTest
{
    public static void main(String[] args)
    {
        List<String> names = new ArrayList<>();
        names.add("Alpha");    
        names.add("Bravo");
        names.add("Charlie");
        names.add("Delta");
        names.add("Echo");

        AssassinManager mgr = new AssassinManager(names);
        mgr.printKillRing();
        System.out.println(mgr.gameOver());
        
        mgr.kill("Alpha");
        mgr.printKillRing();
        mgr.printGraveyard();
    }

}
