
public class LetterInventoryTest
{
    public static void main(String[] args)
    {
        LetterInventory inventory1 = new LetterInventory("George W. Bush");
        LetterInventory inventory2 = new LetterInventory("Hillary Clinton");
        LetterInventory sum = inventory1.add(inventory2);
        System.out.println(inventory1 + "\n" + inventory2 + "\n" + sum);

        System.out.println();
        
        LetterInventory inventory11 = new LetterInventory("Hello world!");
        LetterInventory inventory22 = new LetterInventory("owl");
        LetterInventory difference = inventory11.subtract(inventory22);
        System.out.println(inventory11 + "\n" + inventory22 + "\n" + difference);
    }

}
