package com.company.util;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import com.company.structures.LinkedList;

public class FunctionParseUtils {

    private static final String INVALID_FUNCTION_DEFINITION_MSG = "Expected function definition is <func(a,b)>. Provided: %s";

    private FunctionParseUtils() {}

    public static String parseFunctionName(String definition) {
        return getFunctionDefinitionParts(definition)[0];
    }

    public static List<String> parseFunctionArguments(String definition) {
        String functionArguments = getFunctionDefinitionParts(definition)[1];
        List<String> argsList = new LinkedList<>();
        for (String arg : asList(functionArguments.split(","))) {
            argsList.add(arg);
        }
        return argsList;
    }

    public static List<String> parseFunctionParameters(String parameters,
            Map<String, List<String>> functions) {
        List<String> paramsArr = parseFunctionParametersFromStdin(parameters);

        List<String> finalParams = new LinkedList<>();
        for (String param : paramsArr) {
            if (param.contains("func")) {
                String functionName = parseFunctionName(param);
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

    private static List<String> parseFunctionParametersFromStdin(String parameters) {
        return asList(parameters.split(" "));
    }

    private static String[] getFunctionDefinitionParts(String definition) {
        String[] parts = definition.split("[()]");

        if (parts.length != 2) {
            throw new IllegalArgumentException(format(INVALID_FUNCTION_DEFINITION_MSG, definition));
        }

        return parts;
    }
}
