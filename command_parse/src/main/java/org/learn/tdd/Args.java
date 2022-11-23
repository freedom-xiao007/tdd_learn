package org.learn.tdd;

import org.learn.tdd.option.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Args {

    public static <T> T parse(Class<T> optionsClass) {
        return parse(optionsClass, "");
    }

    public static <T> T parse(Class<T> optionsClass, String args) {
        try {
            Constructor<?> constructor = optionsClass.getConstructors()[0];
            List<String> argsList = Arrays.stream(args.split(" ")).toList();
            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(it, argsList)).toArray();
            return (T) constructor.newInstance(values);
        }
        catch (MoreArgumentsException | ArgumentsTypeErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(Parameter parameter, List<String> argsList) {
        Option option = parameter.getAnnotation(Option.class);
        return parseValue(option, parameter, argsList);
    }

    private static final Map<Class<?>, OptionParser> PARSERS = Map.of(
            boolean.class, SingleValueOptionParser.bool(),
            int.class, SingleValueOptionParser.unary(0, Integer::parseInt),
            String.class, SingleValueOptionParser.unary("/home/logs", String::valueOf)
    );

    private static Object parseValue(Option option, Parameter parameter, List<String> argsList) {
        return PARSERS.get(parameter.getType()).parse(argsList, option);
    }

}
