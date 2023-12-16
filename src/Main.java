import java.util.Scanner;

import static Instruction_Formats.Format.generateInstructions;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the expression: ");
        String expression = sc.next();
        System.out.println("Expression: " + expression);
        System.out.println("Three Address Instructions:");
        System.out.println(generateInstructions(expression,4));

        System.out.println("Two Address Instructions:");
        System.out.println(generateInstructions(expression, 3));

        System.out.println("One Address Instructions:");
        System.out.println(generateInstructions(expression, 2));

        System.out.println("Zero Address Instructions:");
        System.out.println(generateInstructions(expression, 1));

        sc.close();
    }
}