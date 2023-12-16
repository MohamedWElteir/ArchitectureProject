package Instruction_Formats;

import Operations.InfixToPostfix;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Format {

    private static int registerCounter = 1; // Counter to keep track of the number of registers
    private static final Map<String, String> registerMap = new HashMap<>(); // Map to store the register for each operand
    private static final Stack<String> stack = new Stack<>(); // Stack to store the operands
    private static final StringBuilder result = new StringBuilder(); // StringBuilder to store the result

    public static String generateInstructions(String expression, int format) {
        System.out.println("Infix: " + expression);
        String postfix = InfixToPostfix.convertToPostfix(expression);
        String[] postfixTokens = postfix.split(" ");
        StringBuilder result = new StringBuilder();

        switch (format) {
            case 4 -> result.append(generateThreeAddress(postfixTokens));
            case 3 -> result.append(generateTwoAddress(postfixTokens));
            case 2 -> result.append(generateOneAddress(postfixTokens));
            case 1 -> result.append(generateZeroAddress(postfixTokens));
            default -> throw new IllegalArgumentException("Invalid instruction format");
        }

        return result.toString();
    }

    private static String getOrCreateRegister(String operand1, String operand2) {
        String resultRegister;
        if (registerMap.containsKey(operand1) && registerMap.containsKey(operand2)) {
            // If both operands are already in the register map, use the same register
            resultRegister = registerMap.get(operand1);
        } else {
            // If one of the operands is not in the register map, create a new register
            resultRegister = "R" + registerCounter++;
        }
        // Add the result register to the register map
        registerMap.put(operand1, resultRegister);
        registerMap.put(operand2, resultRegister);
        return resultRegister;
    }

    private static String generateThreeAddress(String[] tokens) {
        result.setLength(0);
        for (String token : tokens) {
            if (!isOperator(token)) {
                stack.push(token);
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String resultRegister = getOrCreateRegister(operand1, operand2);
                result.append(getOperationCode(token)).append(" ").append(resultRegister).append(", ").append(operand1).append(", ").append(operand2).append("\n");
                stack.push(resultRegister);
            }
        }

        return result.toString();
    }


    private static String generateTwoAddress(String[] tokens) {
        result.setLength(0);
        for (String token : tokens) {
            if (!isOperator(token)) {
                stack.push(token);
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String resultRegister = getOrCreateRegister(operand1, operand2);

                result.append("LOAD ").append(resultRegister).append(", ").append(operand1).append("\n");
                result.append(getOperationCode(token)).append(" ").append(resultRegister).append(", ").append(operand2).append("\n");

                stack.push(resultRegister);
            }
        }

        return result.toString();
    }


    private static String generateOneAddress(String[] tokens) {
        result.setLength(0);
        stack.clear();
        String tempRegister = "Temp";
        for (String token : tokens) {
            if (!isOperator(token)) {
                stack.push(token);
            } else {
                String operand2 = stack.pop();
                String operand1 = stack.pop();

                if (!operand1.equals(tempRegister)) {
                    // Load operand1 into the temp register only if it's not already there
                    result.append("LOAD ").append(operand1).append("\n");
                }

                // Perform the operation and store the result in the temp register
                result.append(getOperationCode(token)).append(" ").append(operand2).append("\n");

                // Check if the previous operation result is already in the temp register
                if (!stack.isEmpty() && stack.peek().equals(tempRegister)) {
                    // If yes, no need to store again
                    // stack.pop();

                } else {
                    // If not, store the result in the temp register
                    result.append("STORE ").append(tempRegister).append("\n");
                }

                // Push the temp register onto the stack
                stack.push(tempRegister);
            }
        }

        return result.toString();
    }



    private static String generateZeroAddress(String[] tokens) {
        result.setLength(0);
        for (String token : tokens) {
            if (isOperator(token)) {
                result.append(getOperationCode(token)).append("\n");
            } else {
                result.append("PUSH ").append(token).append("\n");
            }
        }
        return result.toString();
    }


    private static String getOperationCode(String operator) {
        return switch (operator) {
            case "+" -> "ADD";
            case "-" -> "SUBT";
            case "*" -> "MULT";
            case "/" -> "DIV";
            case "%" -> "MOD";
            case "=" -> "STORE";
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%") || token.equals("=");
    }
}
