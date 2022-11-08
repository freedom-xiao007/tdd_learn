package org.learn.tdd.option;

import java.util.List;
import java.util.function.Function;

public class SingleValueOptionParser implements OptionParser {

    protected Function<String, Object> parseFunc;
    private Object defaultValue;

    public SingleValueOptionParser(Object defaultValue, Function<String, Object> function) {
        this.defaultValue = defaultValue;
        parseFunc = function;
    }

    @Override
    public Object parse(List<String> argsList, Option option) {
        int index = argsList.indexOf("-" + option.value());
        if (index < 0) {
            return defaultValue;
        }
        if (index + 2 < argsList.size() && !argsList.get(index + 2).startsWith("-")) {
            throw new MoreArgumentsException(option.value());
        }
        String value = argsList.get(index + 1);
        try {
            return parseValue(value);
        } catch (Exception e) {
            throw new ArgumentsTypeErrorException(option.value(), e.getMessage());
        }
    }

    protected Object parseValue(String value) {
        return parseFunc.apply(value);
    }
}
