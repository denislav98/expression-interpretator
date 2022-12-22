package com.company.action;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static com.company.validators.ExpressionValidator.isOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DefineFunctionAction implements IFunctionAction {

    private static final Scanner scanner = new Scanner(System.in);

    private final Map<String, List<String>> functions;

    public DefineFunctionAction(Map<String, List<String>> functions) {
        this.functions = functions;
    }

    @Override
    public void execute() {
        System.out.println("Enter function definition e.g func1(a,b)");
        String functionDefinition = scanner.nextLine(); // extract only func1
        String[] funcDefinitionArr = functionDefinition.split("[()]");
        String functionName = funcDefinitionArr[0];
        String[] functionArgs = funcDefinitionArr[1].split(",");
        System.out.println(
                "Enter function params in Infix(polish) notation e.g. a b &"); // func2(a, b, c): "func1(a, b) | c"

        String functionParams = scanner.nextLine(); // "func1(a, b) | c"
        List<String> paramsArr = asList(functionParams.split(" "));

        List<String> finalParams = parseFunctionParameters(paramsArr);
        validateFunctionParameters(asList(functionArgs), finalParams);

        functions.put(functionName, finalParams);

        System.out.printf("Successfully DEFINED function: %s with parameters %s%n",
                functionDefinition, functionParams);
    }

    private List<String> parseFunctionParameters(List<String> paramsArr) {
        List<String> finalParams = new LinkedList<>();
        for (String param : paramsArr) {
            if (param.contains("func")) {
                String[] functionDefinitionArr = param.split("[()]");
                String functionName = functionDefinitionArr[0];
                List<String> functionParams = functions.get(functionName);
                if (functionParams != null) {
                    finalParams.addAll(functionParams);
                }
            } else {
                finalParams.add(param);
            }
        }
        return finalParams;
    }

    private void validateFunctionParameters(List<String> functionArgs,
            List<String> functionParams) {
        List<String> operands = new ArrayList<>();
        for (String parameter : functionParams) {
            if (!isOperator(parameter)) {
                operands.add(parameter);
            }
        }

        if (functionArgs.size() != operands.size()) {
            List<String> notDefinedOperands = new ArrayList<>(operands);
            notDefinedOperands.removeAll(functionArgs);
            throw new IllegalStateException(
                    format("The following operands: '%s' are not defined", notDefinedOperands));
        }
    }
}
