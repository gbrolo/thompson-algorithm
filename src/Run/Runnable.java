package Run;

import Implementation.AFN;
import Syntax.PostFix;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Runnable.
 * Use this file to generate @AFN.txt, the textfile with the contents of the AFN.
 * Created by Gabriel Brolo on 22/07/2017.
 *
 * NOTE:
 * Valid characters:
 *      Use any symbol rather than '|', '*', '+', '?', '^', '.'
 *      You MUST use ε in your expression for representation of an empty word. (Just copy it from here)
 */
public class Runnable {
    public static String regexp;
    public static void main(String []args) {
        regexp = "(a|ε)b(a+)c?"; // This is the regexp you need to supply
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
