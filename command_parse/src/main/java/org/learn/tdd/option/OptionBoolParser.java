package org.learn.tdd.option;

import java.util.List;

public class OptionBoolParser implements OptionParser {

    @Override
    public Object parse(List<String> argsList, Option option) {
        int index = argsList.indexOf("-" + option.value());
        if (index < 0) {
            return false;
        }
        if(index + 1 < argsList.size() && !argsList.get(index + 1).startsWith("-")) {
            throw new MoreArgumentsException(option.value());
        }
        return true;
    }
}
