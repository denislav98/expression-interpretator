package com.company.action;

import static com.company.ExpressionTreeHelper.buildExpressionTree;
import static com.company.ExpressionTreeHelper.solveExpressionTree;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.company.model.Node;

public class SolveFunctionAction implements IFunctionAction {

    private static final Scanner scanner = new Scanner(System.in);

    private final Map<String, List<String>> functions;

    public SolveFunctionAction(Map<String, List<String>> functions) {
        this.functions = functions;
    }

    @Override
    public void execute() {
        System.out.println("Enter function name to solve e.g func1(0,1)");
        String functionDefinition = scanner.nextLine(); // extract only func1
        String functionName = functionDefinition.split("\\(")[0];
        String[] functionArgs = functionDefinition.split("[()]")[1].split(",");
        List<String> actualFunctionParams = functions.get(functionName);
        Node tree = buildExpressionTree(actualFunctionParams);
        boolean result = solveExpressionTree(tree, functionArgs);
        System.out.println(result);
    }
}
