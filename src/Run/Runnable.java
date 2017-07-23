package Run;

import Implementation.AFN;
import Syntax.PostFix;

/**
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class Runnable {
    public static void main(String []args) {
        AFN afn = new AFN("(a*|b*)c");
        System.out.println(afn.getSymbolList());
        System.out.println(afn.getTransitionsList());
        System.out.println(afn.getFinalStates());
        System.out.println(afn.getStates());
        System.out.println(afn.getInitialState());
    }
}
