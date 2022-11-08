package org.learn.tdd.option;

public class ArgumentsTypeErrorException extends RuntimeException {

    private final String option;

    public ArgumentsTypeErrorException(String option, String message) {
        super(message);
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
