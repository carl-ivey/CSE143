public class MyStack
{
    private ListNode head;
    private int size;

    public MyStack()
    {
        head = null;
        size = 0;
    }

    public void push(int val)
    {
        if (head == null)
        {
            head = new ListNode(val);
        }
        else
        {
            head = new ListNode(val, head);
        }
        size++;
    }

    public int peek()
    {
        if (head == null)
        {
            throw new NullPointerException("Stack is empty!");
        }

        return head.val;
    }

    public int pop()
    {
        if (head == null)
        {
            throw new NullPointerException("Stack is empty!");
        }

        int tmp = head.val;
        head = head.next;
        size--;
        return tmp;
    }
    
    @Override
    public String toString()
    {
        String repStr = "";
        ListNode cur = head;

        while (cur != null)
        {
            repStr += cur.val;
            if (cur.next != null)
            {
                repStr += ", ";
            }
            cur = cur.next;
        }

        return String.format("T> [%s] <B", repStr);
    }
    
    public int size()
    {
        return size;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    static class ListNode
    {
        public ListNode next;
        public int val;

        public ListNode(int val, ListNode next)
        {
            this.val = val;
            this.next = next;
        }

        public ListNode(int val)
        {
            this(val, null);
        }
    }
}
