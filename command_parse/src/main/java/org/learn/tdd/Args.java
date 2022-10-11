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
            Parameter parameter = constructor.getParameters()[0];
            Option option = parameter.getAnnotation(Option.class);
            List<String> argsList = Arrays.stream(args.split(" ")).toList();

            Object value = null;

            if (parameter.getType() == boolean.class) {
                value = argsList.contains("-" + option.value());
            }
            else if (parameter.getType() == int.class) {
                value = Integer.parseInt(argsList.get(1));
            }
            else if (parameter.getType() == String.class) {
                value = argsList.get(1);
            }
            
            return (T) constructor.newInstance(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
