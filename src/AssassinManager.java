
/**
 * Name: AssassinManager.java TA: Kashish Aggarval
 * 
 * Tracks the hierarchy of "killers" and "kills" in the college game known as
 * "assassin" given a list of player names in succeeding order of killer-victim
 * hierarchy, by simulating a "kill ring" tracking the killer-victim hierarchy
 * and a "graveyard" tracking the dead players and their respective killers.
 * 
 * @author Victor Du
 */

import java.util.List;

public class AssassinManager
{
    private AssassinNode killRingFront;
    private AssassinNode killRingBack;

    private AssassinNode graveyardFront;

    /**
     * Initializes an AssassinManager instance given a List<String> of player
     * names in succeeding order of killer-victim hierarchy to track.
     * 
     * @param names,
     *            a List<String> of player names in succeeding order of
     *            killer-victim hierarchy.
     * 
     * @throws IllegalArgumentException
     *             if names is null or empty.
     */
    public AssassinManager(List<String> names)
    {
        if (names == null)
        {
            throw new IllegalArgumentException("names list cannot be null!");
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

    /**
     * Prints the current "kill ring" to the console, which is the hierarchy of
     * assassins that are alive, one assassin per line. If there is only one
     * surviving assassin, in which case the game is over, this function will
     * print that the "<name of surviving assassin> is stalking <name of
     * surviving assassin>."
     */
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

    /**
     * Prints the current list of dead players and their respective killers to
     * the console, one per line.
     */
    public void printGraveyard()
    {
        AssassinNode cur = graveyardFront;

        while (cur != null)
        {
            System.out.printf("    %s was killed by %s\n", cur.name, cur.killer);
            cur = cur.next;
        }
    }

    /**
     * A helper method to check whether an AssassinNode chain contains an
     * assassin of a given name, irrespective of capitalisation.
     * 
     * @param head,
     *            the head node of the AssassinNode chain to search.
     * 
     * @param searchTgt,
     *            the case-insensitive name of the assassin to search for in the
     *            chain.
     * 
     * @return whether the AssassinNode with name searchTgt was found in the
     *         chain.
     */
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

    /**
     * Returns whether the "kill ring" contains any living assassin with a
     * specified case-insensitive name.
     * 
     * @param name,
     *            the case-insensitive name to search the "kill ring" of living
     *            assassins for.
     * 
     * @return whether the "kill ring" contains an assassin with the specified
     *         name.
     */
    public boolean killRingContains(String name)
    {
        return searchAssassinList(killRingFront, name);
    }

    /**
     * Returns whether the graveyard contains any assassin with a specified
     * case-insensitive name.
     * 
     * @param name,
     *            the case-insensitive name to search the graveyard for.
     * 
     * @return whether the graveyard contains an assassin with the specified
     *         name.
     */
    public boolean graveyardContains(String name)
    {
        return searchAssassinList(graveyardFront, name);
    }

    /**
     * Returns whether the game managed by the AssassinManager instance has
     * finished. A game of "assassin" ends when there is only one surviving
     * assassin.
     * 
     * @return whether the game of "assassin" has ended.
     */
    public boolean gameOver()
    {
        return killRingFront != null && killRingFront.next == null;
    }

    /**
     * Returns the name of the winning assassin, or null if the game has not
     * ended yet.
     * 
     * @return the name of the winning assassin
     */
    public String winner()
    {
        if (!gameOver())
        {
            return null;
        }

        return killRingFront.name;
    }

    /**
     * Marks a living assassin in the game as dead by moving them to the
     * graveyard and recording the name of their assigned killer.
     * 
     * @param name,
     *            the case-insensitive name of the assassin to mark as killed.
     * 
     * @throws IllegalStateException
     *             if game has already ended.
     * 
     * @throws IllegalArgumentException
     *             if no assassin specified by the name exists within the kill
     *             ring.
     */
    public void kill(String name)
    {
        if (gameOver())
        {
            throw new IllegalStateException("Game is over, cannot kill more players.");
        }

        if (!killRingContains(name))
        {
            throw new IllegalArgumentException(
                String.format("Cannot kill player \"%s\":" + " player \"%s\" not in kill ring.", name, name));
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
                AssassinNode killer = prev == null ? killRingBack : prev;

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