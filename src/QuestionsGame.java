import java.util.*;
import java.io.*;

public class QuestionsGame
{
    private QuestionNode treeHead;
    private Scanner console;

    public QuestionsGame()
    {
        treeHead = new QuestionNode("computer");
        console = new Scanner(System.in);
    }

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

    public void read(Scanner input)
    {
        treeHead = constructTree(input);
    }

    private void writeTree(QuestionNode cur, PrintStream output)
    {
        if (cur != null)
        {
            boolean isAns = cur.yes == null && cur.no == null;
            output.println(isAns ? "A:" : "Q:");
            output.println(cur.data);
            writeTree(cur.yes, output);
            writeTree(cur.no, output);
        }
    }

    public void write(PrintStream output)
    {
        writeTree(treeHead, output);
    }

    private void executeTree(QuestionNode cur, QuestionNode prev)
    {
        boolean isAns = cur.yes == null && cur.no == null;
        boolean isYes = yesTo(isAns ? "Would your answer happen to be " + cur.data :
                                cur.data);
        
        if (isAns)
        {
            if (isYes)
            {
                System.out.println("Great, I got it right!");
            }
            else
            {
                System.out.println("What is the name of your object?");
                
                String name = console.nextLine();
                
                System.out.println("Please give me a yes/no question that\n"
                    + "distinguishes between your object\n"
                    + "and mine--> ");
                
                String question = console.nextLine();
                
                boolean ansIsYes = yesTo("And what is the answer for your object?");
                
                QuestionNode lastQuestion = new QuestionNode(question);
                QuestionNode conclusion = new QuestionNode(name);
                
                lastQuestion.yes = ansIsYes ? conclusion : cur;
                lastQuestion.no = ansIsYes ? cur : conclusion;
                
                prev.yes = prev.yes == cur ? lastQuestion : prev.yes;
                prev.no = prev.no == cur ? lastQuestion : prev.no;
            }
        }
        else
        {
            //recursive traversal of question tree
            executeTree(isYes ? cur.yes : cur.no, cur);
        }
    }

    public void askQuestions()
    {
        executeTree(treeHead, null);
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