import java.util.*;

public class HangmanManager
{
    private Set<String> wordList;
    private Set<Character> guessedChars;
    private int guessesLeft;
    private String curPattern;
    private String blankPattern;

    public HangmanManager(Collection<String> dictionary, int length, int max)
    {
        if (length < 1)
        {
            throw new IllegalArgumentException("Length cannot be less than 1");
        }
        
        if (max < 0)
        {
            throw new IllegalArgumentException("Max tries cannot be less than 0");
        }
        
        guessesLeft = max;
        guessedChars = new TreeSet<>();
        wordList = new TreeSet<>();

        blankPattern = "";
        
        for (int i = 0; i < length; i++)
        {
            blankPattern += "- ";
        }
        
        blankPattern = blankPattern.trim();
        curPattern = blankPattern;

        for (String s : dictionary)
        {
            if (s.length() == length)
            {
                wordList.add(s);
            }
        }
    }

    public Set<String> words()
    {
        return wordList;
    }

    public int guessesLeft()
    {
        return guessesLeft;
    }

    public Set<Character> guesses()
    {
        return guessedChars;
    }

    public String pattern()
    {
        if (wordList.isEmpty())
        {
            throw new IllegalStateException("Word list can't be empty!");
        }
        
        return curPattern;
    }

    public int record(char guess)
    {
        if (guessesLeft <= 0)
        {
            throw new IllegalStateException("Guesses cannot be less than 0");
        }

        if (wordList.isEmpty())
        {
            throw new IllegalStateException("Word list can't be empty");
        }

        Map<String, Set<String>> famMap = new TreeMap<>();

        for (String s : wordList)
        {
            String famPattern = "";

            for (int i = 0; i < s.length(); i++)
            {
                char c = s.charAt(i);
                famPattern += (c == guess || c == curPattern.charAt(i)) ? c + " " : "- ";
            }
            
            famPattern = famPattern.trim();

            if (famMap.containsKey(famPattern))
            {
                famMap.get(famPattern).add(s);
            }
            else
            {
                Set<String> family = new TreeSet<>();
                family.add(s);
                famMap.put(famPattern, family);
            }
        }

        // locate largest family to set as new word list.
        String bestPattern = null;
        int bestSize = -1;
        Set<String> bestFam = null;

        for (String pattern : famMap.keySet())
        {
            Set<String> fam = famMap.get(pattern);
            int size = fam.size();
            if (size > bestSize)
            {
                bestPattern = pattern;
                bestSize = size;
                bestFam = fam;
            }
        }

        guessedChars.add(guess);

        curPattern = bestPattern;
        wordList = bestFam;
        
        if (bestPattern.equals(blankPattern))
        {
            // handle "wrong" guess (largest family has blank pattern)
            guessesLeft--;
            return 0;
        }

        // handle right guess (largest family has non-blank pattern)
        int occ = 0;

        for (int i = 0; i < bestPattern.length(); i++)
        {
            if (bestPattern.charAt(i) == guess)
            {
                occ++;
            }
        }

        return occ;
    }

}