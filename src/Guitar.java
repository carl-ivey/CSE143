public interface Guitar
{
    public void playNote(int pitch);

    public boolean hasString(char key);

    public void pluck(char key);

    public double sample();

    public void tic();

    public int time();
}