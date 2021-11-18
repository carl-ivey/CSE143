/**
 * Name: AnagramSolver.java
 * TA: Kashish Aggarval
 * 
 * Given a list of words and a word to test, prints all of the 
 * combinations of anagrams of the word to test from the given 
 * list of words.
 * 
 * @author Victor Du
 */

import java.util.*;

public class AnagramSolver
{
    private Map<String, LetterInventory> invMap;
    private List<String> wordList;

    /**
     * Initializes an AnagramSolver instance given a List<String> of
     * words to search for anagrams in.
     * 
     * @param dictionary, the List<String> of words to search for anagrams in.
     */
    public AnagramSolver(List<String> dictionary)
    {
        invMap = new HashMap<>();
        wordList = dictionary;
        
        for (String word : dictionary)
        {
            invMap.put(word, new LetterInventory(word));
        }
    }
    
    /**
     * Internal helper method to prune a given List<String> of words for
     * words that for sure cannot be anagrams of a given String, by
     * containing extraneous characters, and return a List<String> of the
     * contents of the given List<String> with the words containing extraneous
     * characters pruned.
     * 
     * @param text, the String to prune the word list against.
     * @param wordList, the List<String> of words to prune.
     * @return a List<String> of words containing the contents of wordList,
     *          with all impossible Strings pruned.
     */
    private List<String> getPrunedWords(String text, List<String> wordList)
    {
        LetterInventory curInv = new LetterInventory(text);
        List<String> pruned = new ArrayList<>();
        
        for (String word : wordList)
        {
            LetterInventory testInv = invMap.get(word);
            
            if (curInv.subtract(testInv) != null)
            {
                pruned.add(word);
            }
        }
        
        return pruned;
    }    

    /**
     * Internal helper method to print all combinations of words in the given
     * List<String> choices that are anagrams of any word with the contents
     * of a given LetterInventory instance.
     * 
     * @param choices, the List<String> to search anagram combinations inside.
     * @param remaining, the LetterInventory of letter counts of the search word's contents.
     * @param res, a Stack<String>, initially blank,
     *             used to temporarily track the current working anagram combination
     * @param max, the maximum length of combinations to search before ending search.
     * @param limOn, whether to limit the search to a max length.
     */
    private void computeAnagrams(List<String> choices, LetterInventory remaining, 
                                    Stack<String> res, int max, boolean limOn)
    {
        if (remaining != null && ((limOn && max >= 0) || !limOn)) 
        {
            if (remaining.size() == 0)
            {
                System.out.println(res);
            }
            else
            {
                for (String choice : choices)
                {
                    LetterInventory choiceInv = invMap.get(choice);
                    LetterInventory newRem = remaining.subtract(choiceInv);
                    res.push(choice);
                    computeAnagrams(choices, newRem, res, max - 1, limOn);
                    res.pop();
                }
            }
        }
    }
    
    /**
     * Prints all combinations of words in the AnagramSolver that are anagrams of
     * the given String, text.
     * 
     * @param text, the String to print all combinations of anagrams of.
     * @param max, the maximum length of combinations to search,
     *              or zero if all combinations should be printed without length limit.
     * @throws IllegalArgumentException, if max is less than zero.
     */
    public void print(String text, int max)
    {
        if (max < 0)
        {
            throw new IllegalArgumentException(
                "Max cannot be less than zero.");
        }
        
        List<String> prunedList = getPrunedWords(text, wordList);
        computeAnagrams(prunedList, new LetterInventory(text),
            new Stack<String>(), max, max != 0);
    }
}
