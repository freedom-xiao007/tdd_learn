package org.learn.tdd.option;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

public class SingleValueOptionParser {

    public static OptionParser bool() {
        return ((argsList, option) -> values(argsList, option,0).isPresent());
    }

    public static OptionParser unary(Object defaultValue, Function<String, Object> parseFunc) {
        return ((argsList, option) ->
                values(argsList, option,1).map(it -> parseValue(it.get(0), option, parseFunc)).orElse(defaultValue));

    }

    private static Optional<List<String>> values(List<String> argsList, Option option, int exceptionSize) {
        int index = argsList.indexOf("-" + option.value());
        if (index < 0) {
            return Optional.empty();
        }

        int flowingFlag = IntStream.range(index +1, argsList.size())
                .filter(it -> argsList.get(it).startsWith("-"))
                .findFirst()
                .orElse(argsList.size());
        List<String> values = argsList.subList(index +1, flowingFlag);
        if (values.size() > exceptionSize) {
            throw new MoreArgumentsException(option.value());
        }
        return Optional.of(values);
    }

    private static Object parseValue(String value, Option option, Function<String, Object> parseFunc) {
        try {
            return parseFunc.apply(value);
        } catch (Exception e) {
            throw new ArgumentsTypeErrorException(option.value(), e.getMessage());
        }
    }
}
