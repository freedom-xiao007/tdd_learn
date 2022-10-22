package org.learn.tdd.option;

import org.learn.tdd.option.Option;

public record Options(@Option("l")boolean logging, @Option("p")int port, @Option("d")String directory) {
}
