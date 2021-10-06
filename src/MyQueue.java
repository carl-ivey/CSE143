
public class MyQueue
{
    private ListNode head;
    private ListNode tail;
    private int size;

    public MyQueue()
    {
        head = null;
        size = 0;
    }

    public void add(int val)
    {
        if (head == null)
        {
            head = new ListNode(val);
            tail = head;
        }
        else
        {
            tail.next = new ListNode(val);
            tail = tail.next;
        }
        size++;
    }

    public int peek()
    {
        if (head == null)
            throw new NullPointerException("Queue is empty!");
        return head.val;
    }

    public int remove()
    {
        if (head == null)
            throw new NullPointerException("Queue is empty!");
        int tmp = head.val;
        head = head.next;
        size--;
        return tmp;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
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

        return String.format("F> [%s] <B", repStr);
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
