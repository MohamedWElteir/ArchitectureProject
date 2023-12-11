package Instruction_Formats;

import Operations.InfixToPostfix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Format {

    private static int registerCounter = 1;
    private static Map<String, String> registerMap = new HashMap<>(); // Map to store the register for each operand

    public static String generateInstructions(String expression, int format) {
        // Reset register counter for each expression
        registerCounter = 1;
        System.out.println("Expression: " + expression);
        String x = InfixToPostfix.convertToPostfix(expression); //convert string into postfix order
        String[] ARR = x.split("");

        ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(ARR));

        StringBuilder result = new StringBuilder();

        switch (format) {
            case 4:
                result.append(generateThreeAddress(tokens));
                break;
//            case 3:
//                result.append(generateTwoAddress(tokens));
//                break;
//            case 2:
//                result.append(generateOneAddress(tokens));
//                break;
//            case 1:
//                // For zero address, use the postfix expression
//                String postfix = InfixToPostfix.convertToPostfix(expression);
//                String[] postfixTokens = postfix.split(" ");
//                result.append(generateZeroAddress(postfixTokens));
//                break;
            default:
                throw new IllegalArgumentException("Invalid instruction format");
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

    private static String generateThreeAddress(ArrayList<String> tokens) {
        StringBuilder result = new StringBuilder();
        for (int i=0;i<= tokens.size();i+=1)
        {
//            System.out.println(tokens);
//            System.out.println(i);
            if (isOperator(tokens.get(i)))
            {
                String operand1 = tokens.get(i-2);
                String operand2 = tokens.get(i-1);
                String operator = tokens.get(i);

                tokens.remove(i);
                tokens.remove(i-1);
                tokens.remove(i-2);

                String resultRegister = getOrCreateRegister(operand1, operand2);
                tokens.add(i-2,resultRegister);
                result.append(getOperationCode(operator)).append(" ").append(resultRegister)
                        .append(", ").append(operand1).append(", ").append(operand2).append("\n");
            }
        }

        // Reset register map for the next expression
        registerMap.clear();

        return result.toString();
    }




    private static String generateTwoAddress(String[] tokens) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokens.length - 1; i += 2) {
            String operand = tokens[i];
            String operator = tokens[i + 1];
            result.append(getOperationCode(operator)).append(" R1, ").append(operand).append("\n");
        }
        return result.toString();
    }

    private static String generateOneAddress(String[] tokens) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokens.length - 1; i += 2) {
            String operand = tokens[i];
            String operator = tokens[i + 1];
            result.append("LOAD ").append(operand).append("\n");
            result.append("STORE temp\n");
            result.append(getOperationCode(operator)).append(" temp\n");
        }
        return result.toString();
    }

    private static String generateZeroAddress(String[] tokens) {
        StringBuilder result = new StringBuilder();
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
        switch (operator) {
            case "+":
                return "ADD";
            case "-":
                return "SUBT";
            case "*":
                return "MULT";
            case "/":
                return "DIV";
            case "%":
                return "MOD";
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%");
    }
}
