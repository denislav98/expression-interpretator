package com.company;

public class OperandNode extends Node {

    private boolean value;

    public OperandNode(String data) {
        super(data);
    }

    @Override
    public boolean getValue() {
        return value;
    }
}
