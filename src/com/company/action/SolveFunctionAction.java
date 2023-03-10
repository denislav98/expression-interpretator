package com.company.action;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static com.company.ExpressionTreeHelper.buildExpressionTree;
import static com.company.ExpressionTreeHelper.solveExpressionTree;
import static com.company.util.ExpressionUtils.filterOperands;
import static com.company.util.FunctionParseUtils.parseFunctionArguments;
import static com.company.util.FunctionParseUtils.parseFunctionName;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

import com.company.model.Node;

public class SolveFunctionAction implements IFunctionAction {

    private static final String ENTER_FUNCTION_NAME_TO_SOLVE_MSG = "Enter function definition to solve e.g func1(0,1)";

    private static final Scanner scanner = new Scanner(System.in);

    private final Map<String, List<String>> functions;

    public SolveFunctionAction(Map<String, List<String>> functions) {
        this.functions = functions;
    }

    @Override
    public void execute() {
        System.out.println(ENTER_FUNCTION_NAME_TO_SOLVE_MSG);
        String functionDefinition = scanner.nextLine(); // extract only func1
        String functionName = parseFunctionName(functionDefinition);
        List<String> functionArgs = parseFunctionArguments(functionDefinition);

        List<String> functionParams = functions.get(functionName);
        if (functionParams == null) {
            throw new IllegalArgumentException(
                    format("No such function defined: %s", functionName));
        }

        Node tree = buildExpressionTree(functionParams);
        Map<String, String> paramsPerArguments = getFunctionArgsWithParams(functionArgs,
                functionParams);

        boolean result = solveExpressionTree(tree, paramsPerArguments);
        System.out.printf("Result is: %s%n", result ? 1 : 0);
    }

    private Map<String, String> getFunctionArgsWithParams(List<String> args, List<String> params) {
        List<String> operands = filterOperands(params);
        if (args.size() != operands.size()) {
            throw new IllegalStateException(
                    "Mismatch in function arguments and expected parameters");
        }
        return IntStream.range(0, args.size()).boxed().collect(toMap(operands::get, args::get));
    }
}
