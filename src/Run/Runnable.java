package Run;

import Implementation.AFN;
import Syntax.PostFix;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class Runnable {
    public static String regexp;
    public static void main(String []args) {
        regexp = "(b|b)*abb(a|b)*";
        writeFile();
    }

    public static void writeFile() {
        try{
            AFN afn = new AFN(regexp);
            PrintWriter writer = new PrintWriter("AFN.txt", "UTF-8");
            writer.println("REGULAR EXPRESSION: "+regexp);
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
