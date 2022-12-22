package com.company;

import static com.company.model.OperatorType.AND;
import static com.company.model.OperatorType.OR;
import static com.company.validators.ExpressionValidator.isNumber;
import static com.company.validators.ExpressionValidator.isOperator;
import static com.company.validators.ExpressionValidator.obtainOperatorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import com.company.model.Node;
import com.company.model.OperandNode;
import com.company.model.OperatorNode;

public class ExpressionTreeHelper {

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

    // func1(a,b) -> Node(a b &)
    public static Node buildExpressionTree(List<String> functionParams) {
        Stack<Node> st = new Stack<>();
        Node temp;
        for (String currentParam : functionParams) {
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

    public static void inorder(Node root) {
        if (root == null) {
            return;
        }
        inorder(root.getLeft());
        System.out.print(root.getData());
        inorder(root.getRight());
    }

    public static void main(String[] args) {
        // func1(a,b) -> a & b
        Map<String, Node> functions = new HashMap<>();
        Node c = new OperandNode("c");
        Node b = new OperandNode("b");
        Node and = new OperatorNode("&", AND);
        and.setRight(c);
        and.setLeft(b);
        Node a = new OperandNode("a");
        Node or = new OperatorNode("|", OR);
        or.setLeft(a);
        or.setRight(and);

        functions.put("func1(a,b,c)", or);

        List<String> params = new ArrayList<>();
        params.add("func1(a,b,c)");
        params.add("d");
        params.add("|");

        Node r = buildExpressionTree(params);
        boolean result = solveExpressionTree(r, new String[] { "0", "1", "0" });
        System.out.println(result);
        inorder(r);
    }

}
