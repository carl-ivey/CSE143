
public class Nov8
{
    public static void fourAB(String s)
    {
        if (s.length() == 4)
        {
            System.out.println(s);   
        }
        else
        {
            fourAB(s + "a");
            fourAB(s + "b");
        }
    }
    
    public static void main(String[] args)
    {
        fourAB("");
    }
}
