import java.util.*;
import java.io.*;

public class HuffmanCode
{
    private HuffmanNode root;
    
    private HuffmanNode buildHuffTree(PriorityQueue<HuffmanNode> pq)
    {
        while (pq.size() > 1)
        {
            HuffmanNode cur = new HuffmanNode();
            cur.left = pq.remove();
            cur.right = pq.remove();
            cur.frequency = cur.left.frequency + cur.right.frequency;
            pq.add(cur);
        }
        
        return pq.remove();
    }
    
    private HuffmanNode updateHuffTree(HuffmanNode cur, int asciiVal, String order)
    {
        if (cur == null)
        {
            cur = new HuffmanNode();
        }
        
        if (order.length() == 0)
        {
            return new HuffmanNode(asciiVal, 0);
        }
        
        char next = order.charAt(0);
        
        if (next == '0')
        {
            cur.left = updateHuffTree(cur.left, asciiVal, order.substring(1));
        }
        else
        {
            cur.right = updateHuffTree(cur.right, asciiVal, order.substring(1));
        }
        
        return cur;
    }
    
    public HuffmanCode(int[] frequencies)
    {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        
        for (int i = 0; i < frequencies.length; i++)
        {
            if (frequencies[i] != 0)
            {
                pq.add(new HuffmanNode(i, frequencies[i]));   
            }   
        }
        
        root = buildHuffTree(pq);
    }

    public HuffmanCode(Scanner input)
    {
        while (input.hasNextLine())
        {
            int asciiVal = Integer.parseInt(input.nextLine());
            String order = input.nextLine();
            root = updateHuffTree(root, asciiVal, order);
        }
    }
    
    private boolean isLeafNode(HuffmanNode n)
    {
        return n.left == null && n.right == null;
    }

    private void save(HuffmanNode cur, PrintStream output, String path)
    {
        if (cur != null)
        {
            // check if cur is leaf!
            if (isLeafNode(cur))
            {
                output.println(cur.asciiVal);
                output.println(path);
            }
            else
            {
                save(cur.left, output, path + "0");
                save(cur.right, output, path + "1");
            }
        }
    }
    
    public void save(PrintStream output)
    {
        save(root, output, "");
    }

    public void translate(BitInputStream input, PrintStream output)
    {
        HuffmanNode cur = root;
        
        while (input.hasNextBit())
        {
            if (isLeafNode(cur))
            {
                output.print((char) cur.asciiVal);
                cur = root;
            }
            
            int bit = input.nextBit();
            cur = bit == 0 ? cur.left : cur.right;
        }
    }
    
    private static class HuffmanNode implements Comparable<HuffmanNode>
    {
        public int asciiVal;
        public int frequency;
        public HuffmanNode left;
        public HuffmanNode right;
        
        public HuffmanNode()
        {
            // instantiate blank HuffmanNode
            this(-1, -1);
        }
        
        public HuffmanNode(int asciiVal, int frequency)
        {
            this.asciiVal = asciiVal;
            this.frequency = frequency;
        }

        public int compareTo(HuffmanNode other)
        {
            return frequency - other.frequency;
        }
    }
}