import java.util.*;

public class Nov10
{
    
    private static void diceSum(Stack<Integer> chosen, int diceRem, int sumRem)
    {
        if (diceRem == 0)
        {
            if (sumRem == 0)
            {
                System.out.println(chosen);
            }       
        }
        else
        {
            for (int i = 1; i <= 6 && sumRem - i >= 0; i++)
            {
                chosen.push(i);
                diceSum(chosen, diceRem - 1, sumRem - i);
                chosen.pop();
            }
            
        }
    }
    
    public static void diceSum(int dice, int sum)
    {
        diceSum(new Stack<>(), dice, sum);
    }
    
    
    public static void main(String[] args)
    {
        diceSum(3, 7);
    }
}
