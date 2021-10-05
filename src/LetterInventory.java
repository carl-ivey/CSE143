/**
 * Name: LetterInventory.java
 * TA: Kashish Aggarval
 * 
 * This class is instantiated to store the counts of 
 * alphabetical characters in a given String in the constructor.
 * 
 * @author Victor Du
 */
public class LetterInventory
{
    public int[] letterCount;
    public int size;

    /**
     * Instantiates an instance of LetterInventory, representing an inventory of
     * alphabetical (a-z) characters in the given String data irrespective of
     * capitalization.
     * 
     * @param data,
     *            the String data to generate the inventory of characters from
     */
    public LetterInventory(String data)
    {
        letterCount = new int[26];
        size = 0;

        if (data != null)
        {
            for (int i = 0; i < data.length(); i++)
            {
                int dist = Character.toLowerCase(data.charAt(i)) - 'a';
                if (dist >= 0 && dist < 26)
                {
                    letterCount[dist]++;
                    size++;
                }
            }
        }
    }

    /**
     * Instantiates an instance of LetterInventory with all counts of
     * alphabetical characters set to zero.
     */
    public LetterInventory()
    {
        this(null);
    }

    /**
     * Gets the count of instances of the given alphabetical character 'letter'
     * in the LetterInventory instance.
     * 
     * @param letter,
     *            the alphabetical character to get the count of from the
     *            LetterInventory instance.
     * @throws IllegalArgumentException
     *             if letter is non-alphabetical.
     * @return the count of the given letter in the LetterInventory instance.
     */
    public int get(char letter)
    {
        int index = Character.toLowerCase(letter) - 'a';
        
        if (index < 0 || index > 25)
        {
            throw new IllegalArgumentException("Letter added must be alphabetical"
                + " (a-z), case insensitive.");
        }
        
        return letterCount[index];
    }

    /**
     * Sets the count of instances of the given alphabetical character 'letter'
     * in the LetterInventory instance.
     * 
     * @param letter,
     *            the alphabetical character to set the count of in the
     *            LetterInventory instance.
     * @param value,
     *            the count of the alphabetical character to set in the
     *            LetterInventory instance.
     * @throws IllegalArgumentException
     *             if letter is non-alphabetical or if value is negative.
     */
    public void set(char letter, int value)
    {
        int index = Character.toLowerCase(letter) - 'a';
        
        if (index < 0 || index > 25)
        {
            throw new IllegalArgumentException("Letter added must be alphabetical "
                + "(a-z), case insensitive.");
        }
        
        if (value < 0)
        {
            throw new IllegalArgumentException(String.format("Count for char '%c' "
                + "cannot be negative.", letter));
        }
        
        size += value - letterCount[index];
        letterCount[index] = value;
    }

    /**
     * @return the number of letters stored in the LetterInventory instance.
     */
    public int size()
    {
        return size;
    }

    /**
     * @return whether the LetterInventory instance is empty.
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns a String representation of all the letters stored in the
     * LetterInventory instance in sorted order.
     * 
     * @return a String representation of all the letters stored in the
     *         LetterInventory instance in sorted order.
     */
    @Override
    public String toString()
    {
        String invStr = "";
        
        for (int i = 0; i < letterCount.length; i++)
        {
            char actualChar = (char) ('a' + i);
            for (int j = 0; j < letterCount[i]; j++)
            {
                invStr += actualChar;
            }
        }
        
        return String.format("[%s]", invStr);
    }

    /**
     * Returns a new instance of a LetterInventory object combining the letters
     * stored in the current object and another given object.
     * 
     * @param other,
     *            the other LetterInventory object to combine letters from
     * @return a LetterInventory object combining the letters stored in the
     *         current object and the other given object.
     */
    public LetterInventory add(LetterInventory other)
    {
        int[] otherInv = other.letterCount;
        int[] invSum = new int[26];
        int newSize = 0;
        
        for (int i = 0; i < 26; i++)
        {
            int newCount = letterCount[i] + otherInv[i];
            invSum[i] = newCount;
            newSize += newCount;
        }

        LetterInventory result = new LetterInventory();
        result.letterCount = invSum;
        result.size = newSize;
        return result;
    }

    /**
     * Returns a new instance of a LetterInventory object with the letter counts
     * of the other given object subtracted from the letter counts of this
     * LetterInventory object.
     * 
     * @param other,
     *            the other LetterInventory object to subtract letter counts
     *            with
     * @return a new instance of a LetterInventory object with the letter counts
     *         of the other given object subtracted from the letter counts of
     *         this LetterInventory object, or null if any resulting difference
     *         in letter counts is negative.
     */
    public LetterInventory subtract(LetterInventory other)
    {
        int[] otherInv = other.letterCount;
        int[] invDiff = new int[26];
        int newSize = 0;
        
        for (int i = 0; i < 26; i++)
        {
            int diff = letterCount[i] - otherInv[i];
            if (diff < 0)
            {
                return null;
            }
            invDiff[i] = diff;
            newSize += diff;
        }

        LetterInventory result = new LetterInventory();
        result.letterCount = invDiff;
        result.size = newSize;
        return result;
    }

}