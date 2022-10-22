package org.learn.tdd.option;

import java.util.List;

public class OptionIntParser implements OptionParser {

    @Override
    public Object parse(List<String> argsList, Option option) {
        int index = argsList.indexOf("-" + option.value());
        return Integer.parseInt(argsList.get(index + 1));
    }
}
