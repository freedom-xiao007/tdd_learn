package org.learn.tdd.option;

public record Options(@Option("l")boolean logging,
                      @Option("p")int port,
                      @Option("d")String directory) {
}
