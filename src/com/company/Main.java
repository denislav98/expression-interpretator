package com.company;

import static com.company.Command.DEFINE;
import static com.company.Command.SOLVE;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter <DEFINE> to define a function with parameters");
        System.out.println("Enter <SOLVE> to solve a given function");
        String command = scanner.nextLine().toUpperCase();  // DEFINE

        Map<String, Node> functions = new HashMap<>();

        if (DEFINE.equals(command)) {
            System.out.println("Enter function definition e.g func1(a,b)");
            String functionDefinition = scanner.nextLine(); // extract only func1
            String functionName = functionDefinition.split("\\(")[0];
            String functionArgs = functionDefinition.split("[()]")[1];

            System.out.println("Enter function definition e.g func1(a,b)");
            String functionParams = scanner.nextLine(); // "a & b"

            Node tree = ExpressionTreeBuilder.buildExpressionTree(functionParams);
            ExpressionTreeBuilder.solveExpressionTree(tree, new String[] { "0", "1" });
            functions.put(functionDefinition, tree);
            // save as json file
        } else if (SOLVE.equals(command)) {
            System.out.println("Enter function name to solve e.g func1(0,1)");
            String functionDefinition = scanner.nextLine(); // extract only func1
            String functionName = functionDefinition.split("\\(")[0];
            String functionArgs = functionDefinition.split("[()]")[1];
            //  Node tree = ExpressionTreeBuilder.buildExpressionTree(functionParams);
        }
    }
}

