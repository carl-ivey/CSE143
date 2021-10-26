import java.util.*;

public class HangmanManager
{
    private Set<String> wordList;
    private Set<Character> guessedChars;
    private int guessesLeft;
    private String curPattern;

    public HangmanManager(Collection<String> dictionary, int length, int max)
    {
        if (length < 1 || max < 0)
        {
            throw new IllegalArgumentException(
                length < 1 ? "Length cannot be less than 1" : "Max tries cannot be less than 0");
        }

        guessesLeft = max;
        guessedChars = new TreeSet<>();
        wordList = new TreeSet<>();

        curPattern = "";

        for (int i = 0; i < length; i++)
        {
            curPattern += "-";
        }

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

        String formattedPtn = "";

        for (int i = 0; i < curPattern.length(); i++)
        {
            formattedPtn += curPattern.charAt(i);
            if (i != curPattern.length() - 1)
            {
                formattedPtn += " ";
            }
        }

        return formattedPtn;
    }

    private String generatePattern(char guess, String s)
    {
        String pattern = "";

        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            pattern += c == guess ? c : curPattern.charAt(i);
        }

        return pattern;
    }

    private Map<String, Set<String>> classifyFamilies(char guess)
    {
        Map<String, Set<String>> famMap = new TreeMap<>();

        for (String s : wordList)
        {
            String famPattern = generatePattern(guess, s);

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

        return famMap;
    }

    private void chooseBestPattern(Map<String, Set<String>> famMap)
    {
        String bestPattern = null;
        int bestSize = 0;
        Set<String> bestFam = null;

        for (String pattern : famMap.keySet())
        {
            Set<String> fam = famMap.get(pattern);

            int famSize = fam.size();

            if (famSize > bestSize)
            {
                bestPattern = pattern;
                bestSize = famSize;
                bestFam = fam;
            }
        }

        curPattern = bestPattern;
        wordList = bestFam;
    }
    
    private int countOccurances(char guess, String pattern)
    {
        int occ = 0;

        for (int i = 0; i < pattern.length(); i++)
        {
            if (pattern.charAt(i) == guess)
            {
                occ++;
            }
        }

        return occ;
    }

    public int record(char guess)
    {
        if (guessedChars.contains(guess))
        {
            throw new IllegalArgumentException(String.format("Character %c was already guessed!\n", guess));
        }

        if (guessesLeft <= 0 || wordList.isEmpty())
        {
            throw new IllegalStateException(
                guessesLeft <= 0 ? "Guesses must be above 1!" : "Word list can't be empty!");
        }

        guessedChars.add(guess);

        Map<String, Set<String>> famMap = classifyFamilies(guess);

        //System.out.println(famMap);

        // locate largest family to set as new word list.
        chooseBestPattern(famMap);
        //System.out.println("Pattern picked: \"" + bestPattern + "\"");

        int occurances = countOccurances(guess, curPattern);

        if (occurances == 0)
        {
            guessesLeft--;
        }

        return occurances;
    }
}