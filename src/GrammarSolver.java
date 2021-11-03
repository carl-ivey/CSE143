/**
 * Name: GrammarSolver.java
 * TA: Kashish Aggarval
 * 
 * Takes a list of grammar rules in Bauckus-Naur form and generates random
 * sentences that follow the given Bauckus-Naur grammar rules.
 * 
 * @author Victor Du
 */

import java.util.*;

public class GrammarSolver
{
    private SortedMap<String, String[]> ruleMap;
    private Random random;

    /**
     * Initializes a GrammarSolver instance given a List<String> of Bauckus-Naur
     * grammar rules for the GrammarSolver instance to follow, one rule per
     * entry.
     * 
     * @param rules,
     *            a List<String> of Bauckus-Naur grammar rules for the
     *            GrammarSolver instance to follow.
     * 
     * @throws IllegalArgumentException
     *             if rules is empty or contains more than one BNF grammar rule
     *             with the same non-terminal.
     */
    public GrammarSolver(List<String> rules)
    {
        if (rules.isEmpty())
        {
            throw new IllegalArgumentException("Rules list cannot be empty.");
        }

        ruleMap = new TreeMap<>();
        random = new Random();

        for (String rule : rules)
        {
            String[] parts = rule.split("::=");
            String nonTerminal = parts[0];

            if (ruleMap.containsKey(nonTerminal))
            {
                throw new IllegalArgumentException(
                    String.format("More than one entry for nonterminal %s in rules list.", 
                        nonTerminal));
            }

            String[] optionsArr = parts[1].split("\\|");

            for (int i = 0; i < optionsArr.length; i++)
            {
                String option = optionsArr[i];
                optionsArr[i] = option.trim();
            }

            ruleMap.put(nonTerminal, optionsArr);
        }

    }

    /**
     * @param symbol,
     *            the symbol to check whether the GrammarSolver contains a
     *            non-terminal by that name in its grammar rules.
     * 
     * @return whether the GrammarSolver instance contains a grammar rule by the
     *         given non-terminal, symbol.
     */
    public boolean grammarContains(String symbol)
    {
        return ruleMap.containsKey(symbol);
    }

    /**
     * @return a String representation of all non-terminal symbols in the
     *         GrammarSolver instance's grammar rules in the form of a list
     *         that's square bracket-enclosed and comma-separated.
     */
    public String getSymbols()
    {
        return ruleMap.keySet().toString();
    }

    /**
     * Internal helper method to generate a sentence given the non-terminal
     * symbol to start the sentence from.
     * 
     * @param symbol,
     *            the non-terminal symbol to generate the sentence from.
     * 
     * @return a sentence generated from the non-terminal symbol String, symbol.
     */
    private String generate(String symbol)
    {
        String s = "";
        String[] options = ruleMap.get(symbol);

        int randIndex = random.nextInt(options.length);
        String selectedOption = options[randIndex];
        String[] tokens = selectedOption.split("\\s+");

        for (int i = 0; i < tokens.length; i++)
        {
            String token = tokens[i];

            // resolve each token recursively if non-terminal
            s += ruleMap.containsKey(token) ? generate(token) : token;

            if (i != tokens.length - 1)
            {
                s += " ";
            }
        }

        return s;
    }

    /**
     * Procedurally generates a String[] of size times, of sentences compliant
     * with the grammar rules of the GrammarSolver instance from a given
     * non-terminal symbol.
     * 
     * @param symbol,
     *            the non-terminal symbol to generate the sentences from.
     * 
     * @param times,
     *            the number of sentences to generate. Must be greater than
     *            zero.
     * 
     * @throws IllegalArgumentException
     *             if times is negative or if symbol is not a non-terminal.
     * 
     * @return a String[] of sentences compliant with the grammar rules of the
     *         GrammarSolver instance.
     */
    public String[] generate(String symbol, int times)
    {
        if (times < 0 || !ruleMap.containsKey(symbol))
        {
            throw new IllegalArgumentException(
                times < 0 ? "times cannot be negative." : 
                    "symbol must be a non-terminal.");
        }

        String[] result = new String[times];

        for (int i = 0; i < times; i++)
        {
            result[i] = generate(symbol);
        }

        return result;
    }

}