
/**
 * Name: Guitar37.java TA: Kashish Aggarval
 * 
 * Models the behavior of a 37-string guitar by modelling the behavior of its
 * strings, which comprise a frequency range from 110 Hz to 880 Hz, when they
 * are plucked.
 * 
 * @author Victor Du
 */

public class Guitar37 implements Guitar
{
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; // keyboard
                                                                                   // layout
    private GuitarString[] strings;
    private int time;

    /**
     * Instantiates a Guitar37 instance, which models the behavior of a
     * 37-string guitar by modelling the behavior of its strings, which comprise
     * a frequency range from 110 Hz to 880 Hz, when they are plucked.
     */
    public Guitar37()
    {
        strings = new GuitarString[37];
        time = 0;

        for (int i = 0; i < 37; i++)
        {
            double freq = 440.0 * Math.pow(2.0, (i - 24.0) / 12.0);
            strings[i] = new GuitarString(freq);
        }
    }

    public void playNote(int pitch)
    {
        if (pitch >= -24 && pitch <= 12)
        {
            int strIdx = pitch + 24;
            strings[strIdx].pluck();
        }
    }

    public boolean hasString(char key)
    {
        return KEYBOARD.contains(key + "");
    }

    public void pluck(char key)
    {
        int strIdx = KEYBOARD.indexOf(key);

        if (strIdx == -1)
        {
            throw new IllegalArgumentException(String.format("Key %c not in keyboard.", key));
        }

        strings[strIdx].pluck();
    }

    public double sample()
    {
        double total = 0.0;

        for (GuitarString str : strings)
        {
            total += str.sample();
        }

        return total;
    }

    public void tic()
    {
        for (GuitarString str : strings)
        {
            str.tic();
        }
        time++;
    }

    public int time()
    {
        return time;
    }

    // TODO: The rest of your Guitar37 class Here

}