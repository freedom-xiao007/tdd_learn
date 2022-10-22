package org.learn.tdd.option;

import java.util.List;

public class OptionBoolParser implements OptionParser {

    @Override
    public Object parse(List<String> argsList, Option option) {
        return argsList.contains("-" + option.value());
    }
}
