package org.learn.tdd.option;

import java.util.List;

public class OptionStringParser implements OptionParser {

    @Override
    public Object parse(List<String> argsList, Option option) {
        int index = argsList.indexOf("-" + option.value());
        return argsList.get(index + 1);
    }
}
