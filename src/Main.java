import static Instruction_Formats.Format.generateInstructions;

public class Main {
    public static void main(String[] args) {
        String expression = "1 + 2 * 3 - 4 / 5";
        System.out.println("Expression: " + expression);
        System.out.println("Three Address Instructions:");
        System.out.println(generateInstructions(expression,4));

        System.out.println("Two Address Instructions:");
        System.out.println(generateInstructions(expression, 3));

        System.out.println("One Address Instructions:");
        System.out.println(generateInstructions(expression, 2));

        System.out.println("Zero Address Instructions:");
        System.out.println(generateInstructions(expression, 1));
    }
}