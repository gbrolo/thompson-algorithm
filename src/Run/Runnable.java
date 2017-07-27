package Run;

import Implementation.AFN;
import Syntax.PostFix;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Runnable.
 * Use this file to generate @AFN.txt, the textfile with the contents of the AFN.
 * Created by Gabriel Brolo on 22/07/2017.
 *
 * NOTE:
 * Valid characters:
 *      Use any symbol rather than '|', '*', '+', '?', '^', '.'
 *      You MUST use ε in your expression for representation of an empty word. (Just copy it from here)
 *
 * Example of valid regexps:
 *      (a|ε)b(a+)c?
 *      (b|b)*abb(a|b)*
 *      (a*|b*)c
 *      (a|b)*a(a|b)(a|b)
 *      b+abc+
 *      ab*ab*
 */
public class Runnable {
    public static String regexp;
    public static void main(String []args) {
        //regexp = "(a|ε)b(a+)c?"; // This is the regexp you need to supply

        // supply the regexp through user input
        System.out.println("Welcome. Please enter a regexp.");
        System.out.println("NOTE:\n" +
                " * Valid characters:\n" +
                " *      Use any symbol rather than '|', '*', '+', '?', '^', '.'\n" +
                " *      You MUST use ε in your expression for representation of an empty word. (Just copy it from here) \n" +
                " * Example of regexps: \n" +
                " *      0?(1|ε)?0* \n" +
                " *      (b|b)*abb(a|b)* \n" +
                " * Accepts abbreviations and concatenation by yuxtaposition \n" +
                "Enter your regexp after this line:");
        Scanner sc = new Scanner(System.in);

        regexp = sc.nextLine();
        System.out.println("File written to: your_current_directory/AFN.txt");
        System.out.println("© 2017. brolius (Gabriel Brolo)");

        writeFile();
    }

    /**
     * Writes @AFN.txt
     */
    public static void writeFile() {
        try{
            AFN afn = new AFN(regexp);
            PrintWriter writer = new PrintWriter("AFN.txt", "UTF-8");
            writer.println("REGULAR EXPRESSION: "+regexp);
            writer.println("REGULAR EXPRESSION IN POSTFIX: "+afn.getPostFixRegExp());
            writer.println("SYMBOL LIST: "+afn.getSymbolList());
            writer.println("TRANSITIONS LIST: "+afn.getTransitionsList());
            writer.println("FINAL STATE: "+afn.getFinalStates());
            writer.println("STATES: "+afn.getStates());
            writer.println("INITIAL STATE: "+afn.getInitialState());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
