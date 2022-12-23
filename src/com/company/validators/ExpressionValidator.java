package com.company.validators;

import static java.lang.String.format;
import static com.company.model.OperatorType.AND;
import static com.company.model.OperatorType.NOT;
import static com.company.model.OperatorType.OR;

import java.util.ArrayList;
import java.util.List;

import com.company.model.OperatorType;

public class ExpressionValidator {

    private ExpressionValidator() {}

    public static boolean isOperand(String value) {
        try {
            obtainOperatorType(value);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    public static boolean isOperator(String value) {
        try {
            obtainOperatorType(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static OperatorType obtainOperatorType(String str) {
        switch (str) {
        case "&":
            return AND;
        case "|":
            return OR;
        case "!":
            return NOT;
        default:
            throw new IllegalArgumentException(format("Invalid operator given: %s", str));
        }
    }

    public static List<String> filterOperands(List<String> arguments) {
        List<String> operands = new ArrayList<>();
        for (String parameter : arguments) {
            if (isOperator(parameter)) {
                operands.add(parameter);
            }
        }
        return operands;
    }
}
