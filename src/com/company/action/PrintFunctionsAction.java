package com.company.action;

import java.util.List;
import java.util.Map;

public class PrintFunctionsAction implements IFunctionAction {

    private final Map<String, List<String>> functions;

    public PrintFunctionsAction(Map<String, List<String>> functions) {
        this.functions = functions;
    }

    @Override
    public void execute() {
        for (Map.Entry<String, List<String>> entry : functions.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
