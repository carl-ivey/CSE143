
public class QueueStackPractice
{
    public static void main(String[] args)
    {
        testQ();
        testS();
    }
    
    public static void testQ()
    {
        System.out.println("Testing queue");
        MyQueue q = new MyQueue();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        int size = q.size();
        System.out.printf("Size = %d\n", q.size());
        System.out.println(q);
        for (int i = 0; i < size; i++)
        {
            System.out.println(q.remove());
            System.out.println(q);
        }
        System.out.printf("Size = %d\n", q.size());
    }
    
    public static void testS()
    {
        System.out.println("\nTesting stack");
        MyStack s = new MyStack();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        int size = s.size();
        System.out.printf("Size = %d\n", s.size());
        System.out.println(s);
        for (int i = 0; i < size; i++)
        {
            System.out.println(s.pop());
            System.out.println(s);
        }
        System.out.printf("Size = %d\n", s.size());
    }
}
