package Run;

import Implementation.AFN;
import Syntax.PostFix;

/**
 * Created by Gabriel Brolo on 22/07/2017.
 */
public class Runnable {
    public static void main(String []args) {
        AFN afn = new AFN("(b|b)*abb(a|b)*");
        System.out.println(afn.getSymbolList());
        System.out.println(afn.getTransitionsList());
    }
}
