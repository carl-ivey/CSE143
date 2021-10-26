/**
 * Name: HangmanManager.java
 * TA: Kashish Aggarval
 * 
 * Manages decision-making for a rigged game of "hangman" in which the
 * computer-controlled opponent deliberately considers multiple words that can
 * be the secret word, to prolong the guesses made by the user for as long as
 * possible.
 * 
 * @author Victor Du
 */

import java.util.*;

public class HangmanManager
{
    private Set<String> wordList;
    private Set<Character> guessedChars;
    private int guessesLeft;
    private String curPattern;

    /**
     * Initializes a HangmanManager instance given a Collection<String> of
     * possible answers, the length of the word to guess, and the maximum number
     * of wrong guesses the user is allowed to make. Any duplicate words or
     * words not matching the specified length in the given Collection<String>
     * will be ignored.
     * 
     * @param dictionary,
     *            the Collection<String> of possible visits.
     * 
     * @param length,
     *            the length of the word to guess. Must be at least 1.
     * 
     * @param max,
     *            the maximum number of wrong guesses the user is allowed to
     *            make. Must be at least 0.
     * 
     * @throws IllegalArgumentException,
     *             if length is less than 1 or max is less than zero.
     */
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

    /**
     * @return the Set<String> of possible answers corresponding to the current
     *         hangman pattern.
     */
    public Set<String> words()
    {
        return wordList;
    }

    /**
     * @return the number of allowable wrong guesses remaining.
     */
    public int guessesLeft()
    {
        return guessesLeft;
    }

    /**
     * @return the Set<Character> of characters the user has already guessed.
     */
    public Set<Character> guesses()
    {
        return guessedChars;
    }

    /**
     * @return the current word pattern based on existing guesses made by the
     *         user.
     * 
     * @throws IllegalStateException
     *             if there are no possible answers.
     */
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

    /**
     * Internal helper method to generate the hangman word pattern for a given
     * word and the correctly-guessed character.
     * 
     * @param guess,
     *            the character that was correctly guessed.
     * 
     * @param s,
     *            the word to generate the pattern for.
     * 
     * @return the hangman pattern for the given word and the guessed character
     */
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

    /**
     * Internal helper method to map all possible patterns of words for a given
     * guessed character to their respective Set<String> families of possible
     * words.
     * 
     * @param guess,
     *            the guessed character to map the word families with regard to.
     * 
     * @return a Map<String, Set<String>> of all possible patterns of words for
     *         the character guess mapped to their respective families of words.
     */
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

    /**
     * Internal helper method to choose the best pattern in a Map<String,
     * Set<String>> consisting of word patterns mapped to their respective word
     * families, and set the current word pattern of the HangmanManager instance
     * to the pattern chosen. The decision is made by selecting the pattern
     * mapping to the largest family of words in the Map<String, Set<String>>.
     * If all the families have equal corresponding pattern sizes, the first
     * pattern in alphabetical order in the Map will be chosen.
     * 
     * @param famMap,
     *            the map consisting of word patterns mapped to their respective
     *            word families from which to select the chosen pattern.
     */
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

    /**
     * Internal helper method to count the number of occurrences of a specified
     * character, guess, in a given String 'pattern'.
     * 
     * @param guess,
     *            the character to return the count of in the given String.
     * 
     * @param pattern,
     *            the String to search for the character in.
     * 
     * @return the number of occurrences of character 'guess' in String
     *         'pattern'.
     */
    private int countOccurrences(char guess, String pattern)
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

    /**
     * Records that the user made a guess of a given character, 'guess', updates
     * the hangman pattern, and reduces the working set of words to decide from
     * given that character. Finally, returns the number of occurrences of the
     * guessed character within the updated hangman word pattern. If there are
     * no occurrences of the guessed character in the word pattern, the guesses
     * the user has left will be decreased by one.
     * 
     * @param guess,
     *            the character to record the guessing of.
     * 
     * @return the occurrences of the guessed character in the updated hangman
     *         word pattern.
     * 
     * @throws IllegalArgumentException,
     *             if the character 'guess' was already guessed.
     * 
     * @throws IllegalStateException,
     *             if there are no possible words corresponding the current
     *             hangman pattern or if there are no guesses left.
     */
    public int record(char guess)
    {
        if (guessedChars.contains(guess))
        {
            throw new IllegalArgumentException(
                String.format("Character %c was already guessed!\n", guess));
        }

        if (guessesLeft <= 0 || wordList.isEmpty())
        {
            throw new IllegalStateException(
                guessesLeft <= 0 ? "Guesses must be above 1!" : "Word list can't be empty!");
        }

        guessedChars.add(guess);

        Map<String, Set<String>> famMap = classifyFamilies(guess);
        chooseBestPattern(famMap);

        int occurrences = countOccurrences(guess, curPattern);

        if (occurrences == 0)
        {
            guessesLeft--;
        }

        return occurrences;
    }
}