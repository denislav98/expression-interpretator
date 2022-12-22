package com.company.validators;

import static java.lang.String.format;
import static com.company.model.OperatorType.AND;
import static com.company.model.OperatorType.NOT;
import static com.company.model.OperatorType.OR;

import com.company.model.OperatorType;

public class ExpressionValidator {

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

    public static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
