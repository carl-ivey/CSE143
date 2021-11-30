import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HuffmanTest
{
    public static final int MODE = 0;
    
    @SuppressWarnings("all")
    public static void main(String[] args) throws FileNotFoundException
    {
        if (MODE == 0)
        {
            int[] frequencies = new int[1+(int)'z'];
            frequencies[(int)' '] = 1;
            frequencies[(int)'c'] = 1;
            frequencies[(int)'d'] = 1;
            frequencies[(int)'a'] = 2;
            frequencies[(int)'b'] = 2;
            HuffmanCode code = new HuffmanCode(frequencies);
            code.save(System.out);
        }
        else
        {
            HuffmanCode code = new HuffmanCode(new Scanner(new File("huffmanTestFile.txt")));
            code.save(System.out);
        }
    }
    
   
    /*
     public void traverse()
    {
        traverse(root);
    }
    
    // TODO: remove this
    public void traverse(HuffmanNode root)
    {
        Queue<HuffmanNode> q = new LinkedList<>();
        Queue<Integer> lvlQ = new LinkedList<>();
        q.add(root);
        lvlQ.add(0);
        
        int prevLvl = 0;
        while (!q.isEmpty())
        {
            HuffmanNode cur = q.remove();
            int lvl = lvlQ.remove();
            
            if (lvl != prevLvl)
            {
                prevLvl = lvl;
                System.out.println();
            }
            
            System.out.printf("[%s=%d], ", cur.letter == null ? "NULL" : "" + cur.letter, cur.frequency);
            
            if (cur.left != null)
            {
                q.add(cur.left);
                lvlQ.add(lvl + 1);
            }
            
            if (cur.right != null) 
            {
                q.add(cur.right);
                lvlQ.add(lvl + 1);
            }
        }
    }
     */
}
