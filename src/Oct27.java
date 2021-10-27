import java.io.File;

public class Oct27
{
    /**
     * method(5) = method(3) + method(4)
     * method(3) = 1 + method(2)
     * method(2) = method(0) + method(1) = 2
     * method(3) = 1 + 2 = 3
     * method(4) = method(2) + method(3)
     * = 2 + 3 = 5
     * method(3) + method(4) = 3 + 5 = 8
     * 
     * 1 1 2 3 5 8
     * 0,1,2,3,4,5
     * 
     * 
     */
    
    private static int sum(int[] data, int index)
    {
        if (index == 0)
        {
            return data[index];
        }
        return data[index] + sum(data, index - 1);
    }
    
    public static int sum(int[] data)
    {
        if (data.length == 0)
        {
            return 0;
        }
        return sum(data, data.length - 1);
    }
    
    public static int sum2(int[] data)
    {
        if (data.length == 0 || data.length == 1)
        {
            return data.length == 0 ? 0 : data[0];
        }
        
        int[] d2 = new int[data.length - 1];
        for (int i = 0; i < data.length - 1; i++)
        {
            d2[i] = data[i];
        }
        
        return data[data.length - 1] + sum(d2);
    }
    
    private static void print(File furry, int depth)
    {
        for (int i = 0; i < depth; i++)
        {
            System.out.print("\t");
        }
        
        System.out.println(furry);
        
        if (furry.isDirectory())
        {
            for (File file : furry.listFiles())
            {
                print(file, depth + 1);
            }
        }
    }
    
    public static void print(File furry)
    {
        print(furry, 0);
    }
    
    public static void main(String[] args)
    {
        print(new File("."));
       //System.out.println(sum(new int[]{1, 2, 3, 4, 5}));
    }
}
