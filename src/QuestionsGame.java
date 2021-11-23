/**
 * Name: QuestionsGame.java
 * TA: Kashish Aggarval
 * 
 * Handles gameplay for a game of 20 Questions in which
 * the computer attempts to guess a thing the player
 * is thinking about from a database of yes/no questions
 * which can be custom-loaded or dynamically modified as
 * the computer learns more objects from losing.
 * 
 * @author Victor Du
 */

import java.util.*;
import java.io.*;

public class QuestionsGame
{
    private QuestionNode treeHead;
    private Scanner console;

    /**
     * Initializes an instance of QuestionGame, with only one answer for the
     * computer to guess, namely "computer".
     */
    public QuestionsGame()
    {
        treeHead = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

    /**
     * Internal helper method to construct the QuestionsGame data structure from
     * a given Scanner input to read the formatted data from.
     * 
     * @param input,
     *            the Scanner to construct the QuestionsGame data from.
     * 
     * @return a QuestionNode constructed from the data given by input
     */
    private QuestionNode constructTree(Scanner input)
    {
        if (!input.hasNextLine())
        {
            return null;
        }

        boolean isAns = input.nextLine().equals("A:");
        String data = input.nextLine();
        QuestionNode cur = new QuestionNode(data);

        if (!isAns)
        {
            // x = change(x) tree construction
            cur.yes = constructTree(input);
            cur.no = constructTree(input);
        }

        return cur;
    }

    /**
     * Replaces the current contents of the QuestionsGame data structure with
     * those given by the Scanner, input.
     * 
     * @param input,
     *            the Scanner to construct the QuestionsGame data from.
     */
    public void read(Scanner input)
    {
        treeHead = constructTree(input);
    }

    /**
     * Internal helper method to return whether a given QuestionNode is an
     * answer node.
     * 
     * @return whether a given QuestionNode is an answer node.
     */
    private boolean nodeIsAnswer(QuestionNode node)
    {
        return node.yes == null && node.no == null;
    }

    /**
     * Internal helper method to write the contents of a given QuestionNode and
     * its child nodes to a given PrintStream, output.
     * 
     * @param cur,
     *            the QuestionNode to write the contents of.
     * 
     * @param output,
     *            the PrintStream to write the QuestionNode's and constituent
     *            child nodes' contents to.
     */
    private void writeTree(QuestionNode cur, PrintStream output)
    {
        if (cur != null)
        {
            boolean isAns = nodeIsAnswer(cur);
            output.println(isAns ? "A:" : "Q:");
            output.println(cur.data);
            writeTree(cur.yes, output);
            writeTree(cur.no, output);
        }
    }

    /**
     * Outputs the contents of the QuestionsGame' stored data to a PrintStream,
     * output.
     * 
     * @param output,
     *            the PrintStream to output the stored data to.
     */
    public void write(PrintStream output)
    {
        writeTree(treeHead, output);
    }

    /**
     * Internal helper method to handle guessing game gameplay with the user,
     * starting with a given QuestionNode to prompt the user with and asking
     * yes/no questions to narrow down the answer.
     * 
     * @param cur,
     *            the QuestionNode to prompt the user with
     * 
     * @return cur in its original form if the computer's guess is correct, or
     *         an altered QuestionNode accomodating the new guessing object
     *         added to the guessing data.
     */
    private QuestionNode executeTree(QuestionNode cur)
    {
        boolean isAns = nodeIsAnswer(cur);
        boolean isYes = yesTo(isAns ? "Would your object happen to be " + cur.data + "?" : 
                                cur.data);

        if (isAns)
        {
            if (isYes)
            {
                System.out.println("Great, I got it right!");
            }
            else
            {
                System.out.print("What is the name of your object? ");

                String name = console.nextLine();

                System.out.println("Please give me a yes/no question that");
                System.out.println("distinguishes between your object");
                System.out.print("and mine--> ");

                String question = console.nextLine();

                boolean ansIsYes = yesTo("And what is the answer for your object?");

                QuestionNode conclusion = new QuestionNode(name);
                QuestionNode lastQuestion = ansIsYes ? 
                        new QuestionNode(question, conclusion, cur) : 
                        new QuestionNode(question, cur, conclusion);

                return lastQuestion;
            }
        }
        else
        {
            if (isYes)
            {
                cur.yes = executeTree(cur.yes);
            }
            else
            {
                cur.no = executeTree(cur.no);
            }
        }

        return cur;
    }

    /**
     * Executes one round of 20 Questions gameplay, where the computer prompts
     * the user with yes/no questions to guess what object the user is thinking
     * of. If the computer does not sucessfully guess the object the user had
     * in mind, asks the user what object they were thinking of and updates the
     * guessing data of QuestionsGame accordingly.
     */
    public void askQuestions()
    {
        treeHead = executeTree(treeHead);
    }

    // Do not modify this method in any way
    // post: asks the user a question, forcing an answer of "y" or "n";
    // returns true if the answer was yes, returns false otherwise
    private boolean yesTo(String prompt)
    {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n"))
        {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }

    private static class QuestionNode
    {
        public String data;
        public QuestionNode yes;
        public QuestionNode no;

        public QuestionNode(String data)
        {
            this(data, null, null);
        }

        public QuestionNode(String data, QuestionNode yes, QuestionNode no)
        {
            this.data = data;
            this.yes = yes;
            this.no = no;
        }
    }
}