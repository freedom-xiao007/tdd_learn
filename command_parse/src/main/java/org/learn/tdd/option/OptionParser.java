package org.learn.tdd.option;

import java.util.List;

public interface OptionParser {
    Object parse(List<String> argsList, Option option);
}
