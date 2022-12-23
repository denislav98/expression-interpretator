package com.company.action;

import static java.lang.String.format;
import static com.company.action.FunctionParseUtils.parseFunctionArguments;
import static com.company.action.FunctionParseUtils.parseFunctionName;
import static com.company.action.FunctionParseUtils.parseFunctionParameters;
import static com.company.validators.ExpressionValidator.isOperand;
import static com.company.validators.ExpressionValidator.isOperator;
import static com.company.validators.ExpressionValidator.obtainOperatorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
        validateFunctionParameters(functionArgs, functionParams);

        functions.put(functionName, functionParams);

        System.out.printf(SUCCESSFULLY_DEFINED_FUNCTION_MSG, functionDefinition, functionParams);
    }

    private void validateFunctionParameters(List<String> functionArgs,
            List<String> functionParams) {
        List<String> operandParams = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        for (String parameter : functionParams) {
            if (isOperand(parameter)) {
                operandParams.add(parameter);
            } else {
                obtainOperatorType(parameter);
                operators.add(parameter);
            }

            if (parameter.contains("func")) {
                String funcName = parseFunctionName(parameter);
                List<String> funcParams = functions.get(funcName);
                if (funcParams == null) {
                    throw new IllegalArgumentException(
                            format("No such function defined: %s", funcName));
                }
                operandParams.addAll(funcParams);
            }
        }

        int argsSize = functionArgs.size();
        int operandsSize = operandParams.size();
        int operatorsSize = operators.size();

        if (argsSize != operandsSize) {
            List<String> notDefinedOperands = new ArrayList<>(operandParams);
            notDefinedOperands.removeAll(functionArgs);
            throw new IllegalStateException(
                    format("Failed to define function.The following operands: '%s' are not defined",
                            notDefinedOperands));
        }

     /*   if (operandsSize == operatorsSize - 1) {
            throw new IllegalStateException(
                    format("Failed to define function.Mismatch in operators: '%s' and operands: '%s'",
                            operators, operandParams));
        }

      */
    }
}
