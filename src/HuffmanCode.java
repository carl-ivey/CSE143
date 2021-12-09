/**
 * Name: HuffmanCode.java 
 * TA: Kashish Aggarval
 * 
 * Represents a Huffman tree for a set of letters which can be
 * loaded from a Scanner or an array of letter frequencies.
 * 
 * @author Victor Du
 */

import java.util.*;
import java.io.*;
 
public class HuffmanCode
{
    // the root node of the Huffman tree.
    private HuffmanNode root;

    /**
     * Helper method to build a Huffman tree given a generic Queue, 
     * implementation type PriorityQueue, of HuffmanNodes with valid 
     * frequency values.
     * 
     * @param pq,
     *            the Queue<HuffmanNode> to build the Huffman tree from, of
     *            type PriorityQueue.
     *            
     * @return a Huffman tree based on the frequencies of HuffmanNodes in
     *         the given priority queue.
     */
    private HuffmanNode buildHuffTree(Queue<HuffmanNode> pq)
    {
        while (pq.size() > 1)
        {
            // merge nodes of similar size to parent nodes
            HuffmanNode cur = new HuffmanNode();
            cur.left = pq.remove();
            cur.right = pq.remove();
            cur.frequency = cur.left.frequency + cur.right.frequency;
            pq.add(cur);
        }

        return pq.remove();
    }

    /**
     * Constructs a Huffman tree given frequencies, an array in which
     * each index corresponds to an ASCII numeric value for a character and each
     * corresponding value corresponds to the frequency of the character which
     * the index corresponds to.
     * 
     * @param frequencies,
     *            the array which maps ASCII numeric values to their respective
     *            counts to generate the huffman tree from.
     */
    public HuffmanCode(int[] frequencies)
    {
        Queue<HuffmanNode> pq = new PriorityQueue<>();

        for (int i = 0; i < frequencies.length; i++)
        {
            if (frequencies[i] != 0)
            {
                pq.add(new HuffmanNode(i, frequencies[i]));
            }
        }

        root = buildHuffTree(pq);
    }

    /**
     * Helper method to update the contents of a Huffman tree by adding a
     * node with a specified ASCII value and undefined frequency to a specified
     * location in the tree.
     * 
     * @param cur,
     *            the Huffman tree to add the node to.
     *            
     * @param asciiVal,
     *            the ASCII numeric value of the node to add to the tree.
     *            
     * @param order,
     *            a String representing the location of the node to add.
     *            
     * @return the updated Huffman tree with the given node added.
     */
    private HuffmanNode updateHuffTree(HuffmanNode cur, int asciiVal, String order)
    {
        if (order.length() == 0)
        {
            return new HuffmanNode(asciiVal, -1);
        }

        if (cur == null)
        {
            cur = new HuffmanNode();
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

    /**
     * Constructs a Huffman tree from the contents of a Scanner
     * reading pairs of two lines in standard format, where the first line is 
     * the ASCII numeric value of a character and the second line is the location 
     * of the character in the Huffman tree.
     * 
     * @param input,
     *            the Scanner to construct the Huffman tree from.
     */
    public HuffmanCode(Scanner input)
    {
        while (input.hasNextLine())
        {
            int asciiVal = Integer.parseInt(input.nextLine());
            String order = input.nextLine();
            // x = change(x) recursive manipulation of huff tree
            root = updateHuffTree(root, asciiVal, order);
        }
    }

    /**
     * Helper to return whether a given node is a leaf.
     * 
     * @param node,
     *            the HuffmanNode to test for being a leaf.
     *            
     * @return whether node is a leaf.
     */
    private boolean isLeafNode(HuffmanNode node)
    {
        return node.left == null && node.right == null;
    }

    /**
     * Helper method to print the contents of the huffman tree to a given output stream.
     * 
     * @param cur,
     *            the current node to print.
     *            
     * @param output,
     *            the PrintStream to print the leaf node contents of cur and its children
     *            to.
     *            
     * @param path,
     *            the location of the current node to print the tree contents of.
     */
    private void save(HuffmanNode cur, PrintStream output, String path)
    {
        if (cur != null)
        {
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

    /**
     * Prints the huffman tree contents to a output stream in standard format.
     * 
     * @param output,
     *            the PrintStream to print the contents of the huffman tree to.
     */
    public void save(PrintStream output)
    {
        save(root, output, "");
    }

    /**
     * Decodes a Huffman-compressed message given in a BitInputStream and
     * prints the message to a given PrintStream, output.
     * 
     * @param input,
     *            the BitInputStream containing the Huffman-compressed message
     *            
     * @param output,
     *            the PrintStream to print the decoded message to.
     */
    public void translate(BitInputStream input, PrintStream output)
    {
        HuffmanNode cur = root;

        while (input.hasNextBit())
        {
            int bit = input.nextBit();
            cur = bit == 0 ? cur.left : cur.right;

            if (isLeafNode(cur))
            {
                // write resolved ascii char to output stream
                output.write((char) cur.asciiVal);
                cur = root;
            }
        }
    }

    private static class HuffmanNode implements Comparable<HuffmanNode>
    {
        // The ASCII integer value of the character represented by this node
        public int asciiVal;
        
        // The frequency of the character represented by this node.
        public int frequency;
        
        // The node to the left in the tree.
        public HuffmanNode left;
        
        // The node to the right in the tree.
        public HuffmanNode right;

        /**
         * Instantiates a node of the tree with placeholder values for
         * the ASCII integer value of the represented character and
         * the frequency of the represented character.
         */
        public HuffmanNode()
        {
            this(-1, -1);
        }

        /**
         * Instantiates a node given the ASCII integer value of the
         * represented character and the frequency of the represented character.
         * 
         * @param asciiVal, 
         *          the ASCII integer value of the represented character.
         *          
         * @param frequency, 
         *          the frequency of the represented character.
         */
        public HuffmanNode(int asciiVal, int frequency)
        {
            this.asciiVal = asciiVal;
            this.frequency = frequency;
        }

        /**
         * @return an integer value used to compare this node to another
         *          node based on character frequency.
         */
        public int compareTo(HuffmanNode other)
        {
            return frequency - other.frequency;
        }
    }
}