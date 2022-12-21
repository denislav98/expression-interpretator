package com.company;

import static java.lang.String.format;
import static com.company.OperatorType.AND;
import static com.company.OperatorType.NOT;
import static com.company.OperatorType.OR;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ExpressionTreeBuilder {

    public static boolean solveExpressionTree(Node tree, String[] values) {
        for (String value : values) {
            replaceParametersWithActualValues(tree, value);
        }
        return tree.getValue();
    }

    private static void replaceParametersWithActualValues(Node temp, String valueToInsert) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(temp);
        while (!queue.isEmpty()) {
            temp = queue.remove();
            if (temp != null) {
                if (isValueInsertedOnLeft(temp, valueToInsert, queue)) {
                    break;
                }

                if (isValueInsertedOnRight(temp, valueToInsert, queue)) {
                    break;
                }
            }
        }
    }

    private static boolean isValueInsertedOnLeft(Node temp, String valueToInsert,
            Queue<Node> queue) {
        Node left = temp.getLeft();
        if (left != null) {
            boolean isOperator = isOperator(left.getData());
            boolean isNumber = isNumber(left.getData());
            if (!isOperator && !isNumber) {
                temp.getLeft().setData(valueToInsert);
                return true;
            } else {
                queue.add(temp.getLeft());
            }
        }
        return false;
    }

    private static boolean isValueInsertedOnRight(Node temp, String key, Queue<Node> q) {
        Node right = temp.getRight();
        if (temp.getRight() != null) {
            boolean isOperator = isOperator(right.getData());
            boolean isNumber = isNumber(right.getData());
            if (!isOperator && !isNumber) {
                temp.getRight().setData(key);
                return true;
            } else {
                q.add(temp.getRight());
            }
        }
        return false;
    }

    private static boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Node buildExpressionTree(String functionParams) {
        Stack<Node> st = new Stack<>();
        Node temp;

        for (int i = 0; i < functionParams.length(); i++) {
            String currentParam = String.valueOf(functionParams.charAt(i));
            if (!isOperator(currentParam)) {
                temp = new OperandNode(currentParam);
                st.push(temp);
            } else {
                temp = new OperatorNode(currentParam, obtainOperatorType(currentParam));

                Node t1 = st.pop();
                Node t2 = st.pop();

                temp.setLeft(t2);
                temp.setRight(t1);

                st.push(temp);
            }
        }
        temp = st.pop();
        return temp;
    }

    public static boolean isOperator(String operator) {
        try {
            obtainOperatorType(operator);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static OperatorType obtainOperatorType(String str) {
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

    public static void inorder(Node root) {
        if (root == null) {
            return;
        }
        inorder(root.getLeft());
        System.out.print(root.getData());
        inorder(root.getRight());
    }

    public static void main(String[] args) {
        String postfix = "abc&|";

        Node r = buildExpressionTree(postfix);
        boolean result = solveExpressionTree(r, new String[] { "0", "1", "0" });
        System.out.println(result);
        inorder(r);
    }

}
