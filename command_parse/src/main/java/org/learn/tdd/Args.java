package org.learn.tdd;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(Parameter parameter, List<String> argsList) {
        Object value = null;
        Option option = parameter.getAnnotation(Option.class);

        if (parameter.getType() == boolean.class) {
            value = argsList.contains("-" + option.value());
        }
        else if (parameter.getType() == int.class) {
            int index = argsList.indexOf("-" + option.value());
            value = Integer.parseInt(argsList.get(index + 1));
        }
        else if (parameter.getType() == String.class) {
            int index = argsList.indexOf("-" + option.value());
            value = argsList.get(index + 1);
        }
        return value;
    }
}
