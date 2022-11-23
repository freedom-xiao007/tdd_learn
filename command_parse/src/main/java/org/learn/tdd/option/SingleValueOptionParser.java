package org.learn.tdd.option;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SingleValueOptionParser implements OptionParser {

    protected Function<String, Object> parseFunc;
    private final Object defaultValue;

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
        List<String> values = values(argsList, option, index);
        String value = values.get(0);
        try {
            return parseValue(value);
        } catch (Exception e) {
            throw new ArgumentsTypeErrorException(option.value(), e.getMessage());
        }
    }

    private List<String> values(List<String> argsList, Option option, int index) {
        int flowingFlag = IntStream.range(index +1, argsList.size())
                .filter(it -> argsList.get(it).startsWith("-"))
                .findFirst()
                .orElse(argsList.size());
        List<String> values = argsList.subList(index +1, flowingFlag);
        if (values.size() > 1) {
            throw new MoreArgumentsException(option.value());
        }
        return values;
    }

    protected Object parseValue(String value) {
        return parseFunc.apply(value);
    }
}
