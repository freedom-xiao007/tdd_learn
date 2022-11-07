package org.learn.tdd.option;

import java.util.List;
import java.util.function.Function;

public class SingleValueOptionParser implements OptionParser {

    protected Function<String, Object> parseFunc;

    public SingleValueOptionParser(Function<String, Object> function) {
        parseFunc = function;
    }

    @Override
    public Object parse(List<String> argsList, Option option) {
        int index = argsList.indexOf("-" + option.value());
        String value = argsList.get(index + 1);
        return parseValue(value);
    }

    protected Object parseValue(String value) {
        return parseFunc.apply(value);
    }
}
