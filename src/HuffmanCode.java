import java.util.*;
import java.io.*;

public class HuffmanCode
{
    private HuffmanNode root;
    
    private static final int ASCII_SPC = 32;
    private static final int ASCII_A = 97;
    private static final int ASCII_Z = 122;
    
    private HuffmanNode buildHuffTree(PriorityQueue<HuffmanNode> pq)
    {
        if (pq.size() > 1)
        {
            HuffmanNode cur = new HuffmanNode(pq.remove(), pq.remove());
            pq.add(cur);
            return buildHuffTree(pq);
        }
        else
        {
            return pq.remove();
        }
    }
    
    public HuffmanCode(int[] frequencies)
    {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        pq.add(new HuffmanNode(' ', frequencies[ASCII_SPC]));

        for (int i = ASCII_A; i <= ASCII_Z; i++)
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
                output.println(cur.letter);
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

    }

    private static class HuffmanNode implements Comparable<HuffmanNode>
    {
        public Integer letter;
        public int frequency;
        public HuffmanNode left;
        public HuffmanNode right;
        
        public HuffmanNode(HuffmanNode left, HuffmanNode right)
        {
            this.left = left;
            this.right = right;
            this.frequency = left.frequency + right.frequency;
        }

        public HuffmanNode(int letter, int frequency)
        {
            this.letter = letter;
            this.frequency = frequency;
        }

        public int compareTo(HuffmanNode other)
        {
            return frequency - other.frequency;
        }
    }
}