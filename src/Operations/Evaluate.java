package Operations;
import java.util.Stack;


public class Evaluate {
    /**
     * @param expression The expression to be evaluated
     * @return
     */
    public static String evaluate(String expression) {


        if (expression.contains(" ") && endsWithOperator(expression)){ // expression is in postfix form
            return evaluatePostfix(expression);
        }
        else if (isBalanced(expression)) { // expression is in infix form and is balanced
            return evaluatePostfix(InfixToPostfix.convertToPostfix(expression));
        }
        else { // expression is in infix form and is unbalanced
            return "Unbalanced expression";
        }
    }



    private static String evaluatePostfix(String postfixExpression) { // Evaluates a postfix expression
        Stack<Double> operandStack = new Stack<>();


        // The spacing for distinguishing between a single vs multi-digit operands
        for (String token : postfixExpression.split(" ")) {

            if (Character.isDigit(token.charAt(0))) { // If the token is a digit
                operandStack.push(Double.parseDouble(token));
            }
            else if (operandStack.isEmpty() || operandStack.size() == 1) { // In case of an unbalanced postfixExpression
                System.err.println("Invalid Expression");
                return "Invalid Expression";

            }
            else { // If the token is not a digit
                double x = (double) operandStack.pop();
                double y = (double) operandStack.pop();

                /* Switch case for the operators */
                switch (token) {
                    case "+" -> operandStack.push(y + x);
                    case "-" -> operandStack.push(y - x);
                    case "*" -> operandStack.push(y * x);
                    case "%" -> operandStack.push(y % x);
                    case "^" -> operandStack.push(Math.pow(y, x)); // Using the Math.pow() method
                    case "/" -> {
                        if (x == 0) { // In case of division by zero
                            return "Cannot divide by zero";
                        }
                        operandStack.push(y / x);
                    }
                    default -> { // In case of an invalid postfixExpression
                        return "Unbalanced Expression";
                    }
                }
            }
        }

        return String.valueOf(operandStack.pop());
    }
    static boolean endsWithOperator(String expression) { // Checks if the expression ends with an operator
        return  expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith("*") ||
                expression.endsWith("/") ||
                expression.endsWith("^") ||
                expression.endsWith("%");
    }
    // Checks if the expression is in infix or postfix form
    public static String infixOrPostfix(String expression) {
        if (expression.contains(" ") && endsWithOperator(expression))
            return "Notation: postfix";
        else if (expression.contains(" ") && !endsWithOperator(expression))
            return "";
        return "Notation: infix";
    }

    static boolean isBalanced(String expression){ // Checks if the expression is balanced
        int counter = 0;
        for (Character ch : expression.toCharArray()){
            if (ch.equals('(')) counter++;
            if (ch.equals(')')) counter--;
        }
        return counter == 0;

    }
}

