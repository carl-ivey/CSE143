/**
 * Name: Guitar37.java
 * TA: Kashish Aggarval
 * 
 * Models the behavior of a 37-string guitar by modelling the oscillation
 * behavior of its strings, which comprise a frequency range from 110 Hz to 880
 * Hz, when they are plucked.
 * 
 * @author Victor Du
 */

public class Guitar37 implements Guitar
{
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; // keyboard
                                                                                   // layout

    public static final int NUM_STRINGS = 37; // number of strings on guitar

    public static final int PITCH_MIN_VALUE = -24; // lowest playable pitch on
                                                   // guitar

    public static final int PITCH_MAX_VALUE = 12; // highest playable pitch on
                                                  // guitar

    public static final int CONCERT_A_OFFSET = 24;

    private static final double CONCERT_A_FREQ = 440.0; // the frequency for
                                                        // concert-A tone, Hz.

    private GuitarString[] strings;
    private int time;

    /**
     * Instantiates a Guitar37 instance, which models the behavior of a
     * 37-string guitar by modelling the behavior of its strings, which comprise
     * a frequency range from 110 Hz to 880 Hz, when they are plucked.
     */
    public Guitar37()
    {
        strings = new GuitarString[NUM_STRINGS];
        time = 0;

        for (int i = 0; i < NUM_STRINGS; i++)
        {
            double freq = CONCERT_A_FREQ
                * Math.pow(2.0, (i - CONCERT_A_OFFSET) / 12.0);
            strings[i] = new GuitarString(freq);
        }
    }

    /**
     * Simulates the plucking of the string on the 37-string guitar
     * corresponding to a given pitch, in range [-24, 12], where pitch 0
     * corresponds to concert-A tone. If pitch is out of range[-24, 12], the
     * plucking will be ignored without throwing an exception.
     * 
     * @param pitch,
     *            the given pitch to pluck the corresponding guitar string of
     */
    public void playNote(int pitch)
    {
        if (pitch >= PITCH_MIN_VALUE && pitch <= PITCH_MAX_VALUE)
        {
            int strIdx = pitch + CONCERT_A_OFFSET;
            strings[strIdx].pluck();
        }
    }

    /**
     * Returns whether the 37-string guitar contains a string corresponding to
     * the pitch of the given alpha-numerical piano key.
     * 
     * @param key,
     *            the alpha-numerical piano key mapping to check the existence
     *            of in the guitar.
     */
    public boolean hasString(char key)
    {
        return KEYBOARD.contains(key + "");
    }

    /**
     * Plucks the string on the guitar that corresponds to the pitch of the
     * given alpha-numerical piano key.
     * 
     * @param key,
     *            the alpha-numerical piano key mapping to pluck the
     *            corresponding string of in the guitar.
     */
    public void pluck(char key)
    {
        int strIdx = KEYBOARD.indexOf(key);

        if (strIdx == -1)
        {
            throw new IllegalArgumentException(String.format("Key %c not in keyboard.", key));
        }

        strings[strIdx].pluck();
    }

    /**
     * @return the sum of all "sample" displacements of the 37 strings of the
     *         guitar.
     */
    public double sample()
    {
        double total = 0.0;

        for (GuitarString str : strings)
        {
            total += str.sample();
        }

        return total;
    }

    /**
     * Advances the time which the vibration of the guitar's strings is
     * simulated with respect to, thus updating the displacements of the
     * guitar's strings after one time 'step.'
     */
    public void tic()
    {
        for (GuitarString str : strings)
        {
            str.tic();
        }
        time++;
    }

    /**
     * @return the time elapsed since this Guitar37 object was instantiated,
     *         equivalent to the number of times the tic() method was called,
     *         which updates the guitar's string displacements one time 'step.'
     */
    public int time()
    {
        return time;
    }

}