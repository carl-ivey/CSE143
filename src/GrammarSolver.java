import java.util.*;

public class GrammarSolver
{
    private SortedMap<String, String[]> ruleMap;
    private Random random;

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
                    String.format("More than one entry for nonterminal %s in rules list.", nonTerminal));
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

    public boolean grammarContains(String symbol)
    {
        return ruleMap.containsKey(symbol);
    }

    public String getSymbols()
    {
        return ruleMap.keySet().toString();
    }

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
            // resolve each token if non-terminal
            s += ruleMap.containsKey(token) ? generate(token) : token;
            if (i != tokens.length - 1)
            {
                s += " ";
            }
        }

        return s;
    }

    public String[] generate(String symbol, int times)
    {
        if (times < 0 || !ruleMap.containsKey(symbol))
        {
            throw new IllegalArgumentException(
                times < 0 ? "times cannot be negative." : "symbol must be a non-terminal.");
        }

        String[] result = new String[times];

        for (int i = 0; i < times; i++)
        {
            result[i] = generate(symbol);
        }

        return result;
    }

}