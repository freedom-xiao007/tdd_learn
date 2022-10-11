package org.learn.tdd;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    //参数的类型：布尔，数值，字符串，数组(同三种类型？)
    //-l -p 8080 -d /usr/logs -g this -g is -g a -g girl
    // todo:任务分解
    // single:
    //     [x]- bool -l
    //     - int -p 8080
    //     - string -d /usr/logs
    // multi:
    //     -l -p 8080 -d /usr/logs
    // sad path:
    //     - bool -l 80 -d /usr/logs
    //     - int -p 89 89
    //     - string -d /usr/logs /usr/logs
    // default value:
    //     - bool false
    //     - int 0
    //     - string ""

    @Test
    public void test_parse_single_bool_true() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());
    }

    @Test
    public void test_parse_single_bool_false() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    @Test
    public void test_parse_single_int() {
        IntegerOption option = Args.parse(IntegerOption.class, "-p 8080");
        assertEquals(8080, option.port());
    }

    @Test
    public void test_parse_single_string() {
        StringOption option = Args.parse(StringOption.class, "-d /usr/logs");
        assertEquals("/usr/logs", option.directory());
    }

    @Disabled
    public void test() {
        Options options = Args.parse(Options.class, "-l -p 8080 -d /usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }
}
