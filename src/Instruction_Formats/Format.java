package Instruction_Formats;

import Operations.InfixToPostfix;

import java.util.*;

public class Format {

    private static int registerCounter = 1;
    private static Map<String, String> registerMap = new HashMap<>(); // Map to store the register for each operand

    public static void generateInstructions(String expression) {
        // Reset register counter for each expression
        registerCounter = 1;
        System.out.println("Expression: " + expression);
        String x = InfixToPostfix.convertToPostfix(expression);
        String Postfix = addSPACES(x);
        //System.out.println(x);//convert string into postfix order
        //String[] Postfix = x.split("");

//        ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(ARR));

        StringBuilder result = new StringBuilder();

//        switch (format) {
//            case 4:
//                result.append(generateThreeAddress(tokens));
//                break;
//            case 3:
//                result.append(generateTwoAddress(tokens));
//                break;
 //           case 2:
                //result.append(generateOneAddress(x));
        System.out.println("Postfix Expression: "+Postfix);
        generateOneAddress(Postfix);
 //               break;
//            case 1:
//                // For zero address, use the postfix expression
//                String postfix = InfixToPostfix.convertToPostfix(expression);
//                String[] postfixTokens = postfix.split(" ");
//                result.append(generateZeroAddress(postfixTokens));
//                break;
//            default:
//                throw new IllegalArgumentException("Invalid instruction format");
//        }

       // return result.toString();
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

//    private static String generateThreeAddress(ArrayList<String> tokens) {
//        StringBuilder result = new StringBuilder();
//        for (int i=0;i<= tokens.size();i+=1)
//        {
////            System.out.println(tokens);
////            System.out.println(i);
//            if (isOperator(tokens.get(i)))
//            {
//                String operand1 = tokens.get(i-2);
//                String operand2 = tokens.get(i-1);
//                String operator = tokens.get(i);
//
//                tokens.remove(i);
//                tokens.remove(i-1);
//                tokens.remove(i-2);
//
//                String resultRegister = getOrCreateRegister(operand1, operand2);
//                tokens.add(i-2,resultRegister);
//                result.append(getOperationCode(operator)).append(" ").append(resultRegister)
//                        .append(", ").append(operand1).append(", ").append(operand2).append("\n");
//            }
//        }
//
//        // Reset register map for the next expression
//        registerMap.clear();
//
//        return result.toString();
//    }




    private static String generateTwoAddress(String[] tokens) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokens.length - 1; i += 2) {
            String operand = tokens[i];
            String operator = tokens[i + 1];
            result.append(getOperationCode(operator)).append(" R1, ").append(operand).append("\n");
        }
        return result.toString();
    }

    public static void generateOneAddress(String postfix) {

        String[] splited = postfix.split("\\s+");
        Stack<String> stack = new Stack<>();
        int Temp = 0;
        boolean storeTempPrinted = false;

        for (int i = 0; i < splited.length; i++) {
            if (isOperator(splited[i])) {
                if (stack.size() >= 2) {
                    String one = stack.pop();
                    String two = stack.pop();
                    System.out.println("LOAD " + two);
                    System.out.println(getOperationCode(splited[i]) +" "+ one);
                    if (!storeTempPrinted && i != splited.length - 2) {
                        if (!(splited.length ==3)){
                            System.out.println("STORE Temp ");
                            storeTempPrinted = true;
                        }
                    }
                } else if (stack.size() == 1) {
                    System.out.println("LOAD " + stack.peek());
                    System.out.println(getOperationCode(splited[i])+" Temp");
                    //Temp=Temp*sint.pop();
                    if (!storeTempPrinted && i != splited.length - 2) {
                        if (!(splited.length ==3)) {
                            System.out.println("STORE Temp ");
                            storeTempPrinted = true;
                        }
                    }
                } else {
                    System.out.println(getOperationCode(splited[i]) + " Temp");

                }
            }

            else {
                String pk = splited[i];
                stack.push(pk);
            }

            if (i == splited.length - 2 && !storeTempPrinted) {
                if (!(splited.length ==3)){
                    System.out.println("STORE Temp");
                    storeTempPrinted = true;
                }

            }

            if (i == splited.length - 1) {
                System.out.println("STORE Z");
            }
        }
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
    public static String addSPACES(String input) {
        // Check if the input string is not null and has more than one character
        if (input == null || input.length() <= 1) {
            return input;
        }
        StringBuilder result = new StringBuilder();
        result.append(input.charAt(0));

        for (int i = 1; i < input.length(); i++) {
            result.append(" ").append(input.charAt(i));
        }
        return result.toString();
    }
}
