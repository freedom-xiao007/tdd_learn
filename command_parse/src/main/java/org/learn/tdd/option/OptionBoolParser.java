package org.learn.tdd.option;

import java.util.List;

public class OptionBoolParser implements OptionParser {

    @Override
    public Object parse(List<String> argsList, Option option) {
        int index = argsList.indexOf("-" + option.value());
        if (index < 0) {
            return false;
        }
        if(nextStartWithOption(argsList, index)) {
            throw new MoreArgumentsException(option.value());
        }
        return true;
    }

    private boolean nextStartWithOption(List<String> argsList, int index) {
        return index + 1 < argsList.size() && !argsList.get(index + 1).startsWith("-");
    }
}
