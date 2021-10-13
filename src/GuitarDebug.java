
public class GuitarDebug
{
    public static void main(String[] args)
    {
        GuitarString gs = new GuitarString(new double[] { 0, 0.1, 0.2, 0.3, 0.4 });
        gs.print();
        gs.pluck();
        gs.print();

    }

}
