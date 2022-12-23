package com.company.validators;

import static java.lang.String.format;
import static com.company.util.ExpressionUtils.isOperand;
import static com.company.util.ExpressionUtils.obtainOperatorType;
import static com.company.util.FunctionParseUtils.parseFunctionName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionValidator {

    private final Map<String, List<String>> functions;

    public FunctionValidator(Map<String, List<String>> functions) {
        this.functions = functions;
    }

    public void validateParameters(List<String> functionArgs, List<String> functionParams) {
        List<String> operands = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        for (String parameter : functionParams) {
            if (isOperand(parameter)) {
                operands.add(parameter);
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
                operands.addAll(funcParams);
            }
        }

        int argsSize = functionArgs.size();
        int operandsSize = operands.size();

        if (argsSize != operandsSize) {
            List<String> notDefinedOperands = new ArrayList<>(operands);
            notDefinedOperands.removeAll(functionArgs);
            throw new IllegalStateException(
                    format("Failed to define function.The following operands: '%s' are not defined",
                            notDefinedOperands));
        }

   /*     int operatorsSize = operators.size();
        if (operandsSize == operatorsSize - 1) {
            throw new IllegalStateException(
                    format("Failed to define function.Mismatch in operators: '%s' and operands: '%s'",
                            operators, operands));
        }*/
    }
}
