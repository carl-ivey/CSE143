import java.util.*;

public class AnagramSolver
{
    private Map<String, LetterInventory> invMap;
    private List<String> wordList;
    
    private static final boolean PRUNE_EACH_SEARCH_ITER = true;

    public AnagramSolver(List<String> dictionary)
    {
        invMap = new HashMap<>();
        wordList = dictionary;
        
        for (String word : dictionary)
        {
            invMap.put(word, new LetterInventory(word));
        }
    }
    
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

    private void computeAnagrams(String anagramTarget, List<String> choices, LetterInventory remaining, 
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
                    List<String> nextChoices = PRUNE_EACH_SEARCH_ITER ? 
                        getPrunedWords(anagramTarget, choices) : choices;
                    computeAnagrams(anagramTarget, nextChoices, newRem, res, max - 1, limOn);
                    res.pop();
                }
            }
        }
    }
    
    public void print(String text, int max)
    {
        if (max < 0)
        {
            throw new IllegalArgumentException();
        }
        
        List<String> prunedList = getPrunedWords(text, wordList);
        computeAnagrams(text, prunedList, new LetterInventory(text), 
            new Stack<String>(), max, max != 0);
    }
}
