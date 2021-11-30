import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class HuffmanTest
{
    public static final String FILE_NAME = "huffmanTestFile.txt";
    
    @SuppressWarnings("all")
    public static void main(String[] args) throws FileNotFoundException
    {
        File f = new File(FILE_NAME);
        int[] frequencies = new int[255];
        frequencies[(int)' '] = 1;
        frequencies[(int)'c'] = 1;
        frequencies[(int)'d'] = 1;
        frequencies[(int)'a'] = 2;
        frequencies[(int)'b'] = 2;
        HuffmanCode code = new HuffmanCode(frequencies);
        code.save(System.out);
        PrintStream out = new PrintStream(f);
        code.save(out);
        out.close();
        
        System.out.println("\n");
        
        HuffmanCode code2 = new HuffmanCode(new Scanner(new File(FILE_NAME)));
        code2.save(System.out);
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
