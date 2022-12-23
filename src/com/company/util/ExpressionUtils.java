package com.company.util;

import static java.lang.String.format;
import static com.company.model.OperatorType.AND;
import static com.company.model.OperatorType.NOT;
import static com.company.model.OperatorType.OR;

import java.util.ArrayList;
import java.util.List;

import com.company.model.OperatorType;

public class ExpressionUtils {

    private ExpressionUtils() {}

    public static boolean isOperand(String value) {
        if (value == null || value.length() != 1) {
            return false;
        }

        return Character.isLetter(value.charAt(0));
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
            throw new IllegalArgumentException(format("Invalid operator given: '%s'", str));
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
