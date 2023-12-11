package Operations;
import java.util.Stack;


import java.util.StringJoiner;

import static Operations.Evaluate.endsWithOperator;

// main method to convert from infix to postfix
public class InfixToPostfix {

    public static String convertToPostfix(String expression) {

        String result = "";
        Stack<Character> stack1 = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            Character c = expression.charAt(i);

            if (c.isLetterOrDigit(c))
            {
                result += c;
            }

            else if (c == '(')
                stack1.push(c);

            else if (c == ')')
            {
                while (!stack1.isEmpty() && stack1.peek() != '('){
                    result += stack1.peek();
                    stack1.pop();
                }

                stack1.pop();
            }
            else
            {
                while (!stack1.isEmpty() && getPrecedence(c) <= getPrecedence(stack1.peek()))
                {
                    result += stack1.peek();
                    stack1.pop();
                }
                stack1.push(c);
            }
        }

        while (!stack1.isEmpty()) {
            if (stack1.peek() == '(')
                return "Invalid Expression";
            result += stack1.peek();
            stack1.pop();
        }

        return result;
    }
    // Comparing the precedence of the operators
    private static int getPrecedence(char c) {
        return switch (c) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            case '%' -> 4;
            default -> 0;
        };
    }


}
