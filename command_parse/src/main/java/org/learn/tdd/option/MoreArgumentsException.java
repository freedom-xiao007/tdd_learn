package org.learn.tdd.option;

public class MoreArgumentsException extends RuntimeException {

    private final String option;

    public MoreArgumentsException(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
