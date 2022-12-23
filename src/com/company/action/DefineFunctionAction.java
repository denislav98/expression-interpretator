package com.company.action;

import static com.company.util.FunctionParseUtils.parseFunctionArguments;
import static com.company.util.FunctionParseUtils.parseFunctionName;
import static com.company.util.FunctionParseUtils.parseFunctionParameters;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.company.validators.FunctionValidator;

public class DefineFunctionAction implements IFunctionAction {

    private static final String ENTER_FUNCTION_DEFINITION_MSG = "Enter function definition e.g func1(a,b)";
    private static final String ENTER_FUNCTION_PARAMS_MSG = "Enter function params in Infix notation e.g. a b &";
    private static final String SUCCESSFULLY_DEFINED_FUNCTION_MSG = "Successfully DEFINED function: %s with parameters %s%n";

    private static final Scanner scanner = new Scanner(System.in);

    private final Map<String, List<String>> functions;

    public DefineFunctionAction(Map<String, List<String>> functions) {
        this.functions = functions;
    }

    @Override
    public void execute() {
        System.out.println(ENTER_FUNCTION_DEFINITION_MSG);
        String functionDefinition = scanner.nextLine();
        String functionName = parseFunctionName(functionDefinition);
        List<String> functionArgs = parseFunctionArguments(functionDefinition);

        System.out.println(ENTER_FUNCTION_PARAMS_MSG); // func2(a, b, c): "func1(a, b) | c"
        String functionParamsFromStdin = scanner.nextLine(); // "func1(a,b) | c"

        List<String> functionParams = parseFunctionParameters(functionParamsFromStdin, functions);
        new FunctionValidator(functions).validateParameters(functionArgs, functionParams);

        functions.put(functionName, functionParams);

        System.out.printf(SUCCESSFULLY_DEFINED_FUNCTION_MSG, functionDefinition, functionParams);
    }

}
