package com.company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.company.action.DefineFunctionAction;
import com.company.action.IFunctionAction;
import com.company.action.PrintFunctionsAction;
import com.company.action.SolveFunctionAction;

public class Main {

    public static void main(String[] args) {
        Map<String, List<String>> functions = new HashMap<>();

        while (true) {
            try {
                System.out.println("Options: \n" +
                        "1. DEFINE function\n" +
                        "2. SOLVE function \n" +
                        "3. PRINT functions \n" +
                        "4. EXIT");

                int choice = Integer.parseInt(new Scanner(System.in).nextLine());

                if (choice == 1) {
                    IFunctionAction defineAction = new DefineFunctionAction(functions);
                    defineAction.execute();
                } else if (choice == 2) {
                    IFunctionAction action = new SolveFunctionAction(functions);
                    action.execute();
                } else if (choice == 3) {
                    IFunctionAction action = new PrintFunctionsAction(functions);
                    action.execute();
                } else if (choice == 4) {
                    return;
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

