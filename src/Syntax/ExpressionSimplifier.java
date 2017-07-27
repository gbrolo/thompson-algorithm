package Syntax;

/**
 * Expression Simplifier
 * Checks for '+' and '?' operators inside a regexp. If true, replaces them in terms of '*' and '|ε'.
 * Created by Gabriel Brolo on 23/07/2017.
 */
public class ExpressionSimplifier {
    private String regExp; // the regexp

    public ExpressionSimplifier (String regExp) {
        this.regExp = regExp;
        handleKleeneSum();
        handleLua();
    }

    public String getRegExp() {
        return this.regExp;
    }

    /**
     * Handles '?' operator
     */
    public void handleLua() {
        // traverse expression
        for (int i = 0; i < regExp.length(); i++) {
            if (Character.toString(regExp.charAt(i)).equals("?")) {
                // two cases can arise:
                // 1. ? is after a symbol, in which case ? afects only that symbol
                // 2. ? is after a ')', in which case ? afects a whole expresion inside '(' and ')'

                // case ( 1 )
                if (!Character.toString(regExp.charAt(i-1)).equals(")")) {
                    String symbol = Character.toString(regExp.charAt(i-1));
                    String subExpression = "(" + symbol + "|ε)";

                    String left = regExp.substring(0, i-1);
                    String right = regExp.substring(i+1);
                    regExp = left+subExpression+right;
                } else {
                    // case ( 2 )
                    // first find lower bound
                    for (int j = (i-1); j >= 0; j--) {
                        if ((Character.toString(regExp.charAt(j)).equals("("))) {
                            // stop
                            if (j != 0) {
                                String symbolSequence = (String) regExp.subSequence(j+1, i-1);
                                String symbolSequenceWithBrackets = (String) regExp.subSequence(j, i);
                                String subExp = "(" + symbolSequenceWithBrackets + "|ε)";

                                String left = regExp.substring(0, j);
                                String right = regExp.substring(i+1);
                                regExp = left+subExp+right;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * handles '+' operator
     */
    public void handleKleeneSum() {
        // traverse expression
        for (int i = 0; i < regExp.length(); i++) {
            if (Character.toString(regExp.charAt(i)).equals("+")) {
                // two cases can arise:
                // 1. + is after a symbol, in which case + afects only that symbol
                // 2. + is after a ')', in which case + afects a whole expresion inside '(' and ')'

                // case ( 1 )
                if (!Character.toString(regExp.charAt(i-1)).equals(")")) {
                    String symbol = Character.toString(regExp.charAt(i-1));
                    String subExpression = symbol + symbol + "*";

                    String left = regExp.substring(0, i-1);
                    String right = regExp.substring(i+1);
                    regExp = left+subExpression+right;
                } else {
                    // case ( 2 )
                    // first find lower bound
                    int bracketCounter = 0;
                    for (int j = (i-1); j >= 0; j--) {
                        if ((j != (i-1)) && (Character.toString(regExp.charAt(j)).equals(")"))) {
                            bracketCounter++;
                        }
                        // stop
                        if ((Character.toString(regExp.charAt(j)).equals("("))) {
                            if (bracketCounter != 0) {
                                bracketCounter--;
                            } else {
                                String symbolSequence = (String) regExp.subSequence(j+1, i-1);
                                String symbolSequenceWithBrackets = (String) regExp.subSequence(j, i);
                                String subExp = symbolSequenceWithBrackets + symbolSequenceWithBrackets + "*";

                                String left = regExp.substring(0, j);
                                String right = regExp.substring(i+1);
                                regExp = left+subExp+right;
                            }

                            if( j != 0) {

                            }
                        }
                    }
                }
            }
        }
    }
}
