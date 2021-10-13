/**
 * Name: GuitarString.java 
 * TA: Kashish Aggarval
 * 
 * Models the behavior of a plucked guitar string that is vibrating, given a
 * frequency or a list of displacement values of sequential segments in time of
 * the string to model.
 * 
 * @author Victor Du
 */

import java.util.*;

public class GuitarString
{
    private Queue<Double> ringBuffer;
    public static final double ENERGY_DECAY_FACTOR = 0.996;

    /**
     * Instantiates an instance of GuitarString, modeling the behavior of a
     * plucked guitar string from a given frequency.
     * 
     * @param frequency,
     *            the frequency of the guitar string to model
     * @throws IllegalArgumentException
     *             if frequency is below zero or if the capacity of the ring
     *             buffer used to simulate the guitar string is less than two.
     */
    public GuitarString(double frequency)
    {
        if (frequency <= 0.0)
            throw new IllegalArgumentException(
                String.format("Frequency must be above zero. (your frequency: %.3f)", frequency));

        int capacity = (int) Math.round((double) StdAudio.SAMPLE_RATE / frequency);

        if (capacity < 2)
        {
            double maxFreq = StdAudio.SAMPLE_RATE / 2.0;
            throw new IllegalArgumentException(
                String.format("Buffer capacity must be greater than 2. (maximum frequency: %.3f, your frequency: %.3f)",
                    maxFreq, frequency));
        }

        ringBuffer = new LinkedList<>();

        for (int i = 0; i < capacity; i++)
        {
            ringBuffer.add(0.0);
        }
    }

    /**
     * Instantiates an instance of GuitarString, modeling the behavior of a
     * plucked guitar string from an array of string displacements in range
     * [-0.5, 0.5) in sequential order with respect to time.
     * 
     * @param init,
     *            the array of sequential string displacements in range [-0.5,
     *            0.5) to model the guitar string from
     * @throws IllegalArgumentException
     *             if the given array has less than two elements.
     */
    public GuitarString(double[] init)
    {
        int length = init.length;

        if (length < 2)
        {
            throw new IllegalArgumentException(String
                .format("Ring buffer argument must have at least 2 elements. (currently has %d elements)", length));
        }

        ringBuffer = new LinkedList<>();

        for (int i = 0; i < length; i++)
        {
            ringBuffer.add(init[i]);
        }
    }

    /**
     * Replaces all current displacements in the ring buffer modelling the
     * guitar string with random values in range [-0.5, 0.5).
     */
    public void pluck()
    {
        int cap = ringBuffer.size();

        for (int i = 0; i < cap; i++)
        {
            ringBuffer.remove();
            ringBuffer.add(-0.5 + Math.random());
        }
    }

    /**
     * Applies the Karplus-Strong update to the ring buffer modelling the guitar
     * string by removing the front displacement from the ring buffer, averaging
     * it with the second displacement preceding the front displacement, and
     * adding the average multiplied by the energy decay factor to the back of
     * the ring buffer.
     */
    public void tic()
    {
        double frontDx = ringBuffer.remove();
        double secondDx = ringBuffer.peek();
        double scaledAverage = ENERGY_DECAY_FACTOR * 0.5 * (frontDx + secondDx);
        ringBuffer.add(scaledAverage);
    }

    /**
     * Returns the current sample displacement of the guitar string, which is
     * the front displacement value in the ring buffer modelling the guitar
     * string instance.
     * 
     * @return the current sample displacement of the guitar string
     */
    public double sample()
    {
        return ringBuffer.peek();
    }

    /**
     * Prints a String representation of the displacements in the ring buffer
     * modelling the guitar string.
     */
    public void print()
    {
        System.out.println(ringBuffer);
    }

}
