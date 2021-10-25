
// nama, nelson, shritar, victor, wei-fan
public class Oct25
{
    public static void main(String[] args)
    {
        // 1 2 3 4 5
        // 1 1 2 3 5
        //    fib(4) = fib(3) + fib(2)
        //    fib(3) = fib(2) + fib(1) = 2
        //    fib(2) = fib(1) + fib(0) = 1
        //    fib(4) = 2 + 1 = 3
        for (int i = 1; i <= 20; i++)
        {
            System.out.println(fibonacci(i));
        }
    }
    
    public static int fibonacci(int n)
    {
        if (n <= 1)
        {
            return n;
        }
        
        return fibonacci(n-1) + fibonacci(n-2);
    }
}
