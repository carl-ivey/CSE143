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
        int[] frequencies = new int[1+(int)'z'];
        frequencies[(int)' '] = 1;
        frequencies[(int)'c'] = 1;
        frequencies[(int)'d'] = 1;
        frequencies[(int)'a'] = 2;
        frequencies[(int)'b'] = 2;
        HuffmanCode code = new HuffmanCode(frequencies);
        //code.traverse();
        code.save(System.out);
        PrintStream out = new PrintStream(f);
        code.save(out);
        out.close();
        
        System.out.println("\n");
        
        HuffmanCode code2 = new HuffmanCode(new Scanner(new File(FILE_NAME)));
        code2.traverse();
        code2.save(System.out);
    }
    
   
    /*
     
    }
     */
}
