import java.util.List;

public class AssassinManager
{
    private AssassinNode killRingFront;
    private AssassinNode killRingBack;
    
    private AssassinNode graveyardFront;

    public AssassinManager(List<String> names)
    {
        if (names == null)
        {
            throw new IllegalArgumentException("names cannot be null!");
        }
        
        if (names.isEmpty())
        {
            throw new IllegalArgumentException("names cannot be an empty list!");
        }
        
        AssassinNode prev = null;

        for (String name : names)
        {
            if (killRingFront == null)
            {
                killRingFront = new AssassinNode(name);
                prev = killRingFront;
            }
            else
            {
                AssassinNode node = new AssassinNode(name);
                prev.next = node;
                prev = node;
                killRingBack = node;
            }
        }
    }

    public void printKillRing()
    {
        AssassinNode cur = killRingFront;

        while (cur != null)
        {
            System.out.printf("    %s is stalking %s\n", cur.name, 
                cur.next == null ? killRingFront.name : cur.next.name);
            cur = cur.next;
        }
    }
    
    public void printGraveyard()
    {
        AssassinNode cur = graveyardFront;
        
        while (cur != null)
        {
            System.out.printf("    %s was killed by %s\n", cur.name,
                cur.killer);
            cur = cur.next;
        }
    }
    
    private boolean searchAssassinList(AssassinNode head, String searchTgt)
    {
        AssassinNode cur = head;

        while (cur != null)
        {
            String searchNameLower = searchTgt.toLowerCase();
            String curNameLower = cur.name.toLowerCase();
            
            if (searchNameLower.equals(curNameLower))
            {
                return true;
            }
            
            cur = cur.next;
        }
        
        return false;
    }
    
    public boolean killRingContains(String name)
    {
        return searchAssassinList(killRingFront, name);
    }
    
    public boolean graveyardContains(String name)
    {
        return searchAssassinList(graveyardFront, name);
    }
    
    public boolean gameOver()
    {
        return killRingFront != null && killRingFront.next == null;
    }
    
    public String winner()
    {
        if (!gameOver())
        {
            return null;
        }
        
        return killRingFront.name;
    }
    
    public void kill(String name)
    {
        if (gameOver())
        {
            throw new IllegalStateException("Game is over, cannot kill more players.");
        }
        
        if (!killRingContains(name))
        {
            throw new IllegalArgumentException(String.format("Cannot kill player \"%s\":"
                + " player \"%s\" not in kill ring.", name, name));
        }
        
        AssassinNode cur = killRingFront;
        AssassinNode prev = null;
        boolean targetFound = false;
        
        while (cur != null && !targetFound)
        {
            String searchNameLower = name.toLowerCase();
            String curNameLower = cur.name.toLowerCase();
            
            if (searchNameLower.equals(curNameLower))
            {
                AssassinNode killer = prev == null ? 
                    killRingBack : prev;
                    
                cur.killer = killer.name;
                
                if (prev == null)
                {
                    killRingFront = cur.next;
                }
                else
                {
                    prev.next = cur.next;
                }
                
                AssassinNode prevGraveFront = graveyardFront;
                graveyardFront = cur;
                graveyardFront.next = prevGraveFront;
                targetFound = true;
            }
            else
            {
                prev = cur;
                cur = cur.next;
            }
        }
        
    }
}