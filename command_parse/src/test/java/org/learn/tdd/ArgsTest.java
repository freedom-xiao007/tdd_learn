package org.learn.tdd;

import org.junit.jupiter.api.Test;
import org.learn.tdd.option.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 单个参数时需要具体到对应的Option
 * 不可：Options option = Args.parse(Options.class, "-p 80");
 * 应为：IntegerOption option = Args.parse(IntegerOption.class, "-p 8080");
 */
public class ArgsTest {

    //参数的类型：布尔，数值，字符串，数组(同三种类型？)
    //-l -p 8080 -d /usr/logs -g this -g is -g a -g girl
    // todo:任务分解
    // single:
    //     [x]- bool -l
    //     [x]- int -p 8080
    //     [x]- string -d /usr/logs
    // multi:
    //     -l -p 8080 -d /usr/logs
    @Test
    public void test_parse_single_bool_true() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());
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

    @Test
    public void test_parse_multi() {
        Options options = Args.parse(Options.class, "-l -p 8080 -d /usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    // sad path:
    //     - bool -l 80 -d /usr/logs
    //     - port -p 89 89
    //     - port -p string
    //     - dir  -d /usr /log
    @Test
    public void test_single_logging_input_more_error() {
        MoreArgumentsException e = assertThrows(MoreArgumentsException.class, () -> Args.parse(BooleanOption.class, "-l 80"));
        assertEquals("l", e.getOption());
    }

    @Test
    public void test_more_logging_input_more_error() {
        MoreArgumentsException e = assertThrows(MoreArgumentsException.class, () -> Args.parse(Options.class, "-l 80 -d /usr"));
        assertEquals("l", e.getOption());
    }

    @Test
    public void test_single_port_input_more_error() {
        MoreArgumentsException e = assertThrows(MoreArgumentsException.class, () -> Args.parse(Options.class, "-p 90 90 "));
        assertEquals("p", e.getOption());
    }

    @Test
    public void test_more_port_input_more_error() {
        MoreArgumentsException e = assertThrows(MoreArgumentsException.class, () -> Args.parse(Options.class, "-l -p 90 90 -d /usr"));
        assertEquals("p", e.getOption());
    }

    @Test
    public void test_single_port_input_type_error() {
        ArgumentsTypeErrorException e = assertThrows(ArgumentsTypeErrorException.class, () -> Args.parse(Options.class, "-p string"));
        assertEquals("p", e.getOption());
    }

    @Test
    public void test_more_port_input_type_error() {
        ArgumentsTypeErrorException e = assertThrows(ArgumentsTypeErrorException.class, () -> Args.parse(Options.class, "-l -p string -d /usr"));
        assertEquals("p", e.getOption());
    }

    @Test
    public void test_single_dir_input_more_error() {
        MoreArgumentsException e = assertThrows(MoreArgumentsException.class, () -> Args.parse(Options.class, "-d /usr /log "));
        assertEquals("d", e.getOption());
    }

    @Test
    public void test_more_dir_input_more_error() {
        MoreArgumentsException e = assertThrows(MoreArgumentsException.class, () -> Args.parse(Options.class, "-l -p 90 -d /usr /log"));
        assertEquals("d", e.getOption());
    }

    // default value:
    //     - bool false
    //     - int 0
    //     - string "/home/logs"
    @Test
    public void test_parse_single_empty_logging_false() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    @Test
    public void test_parse_more_empty_loggging_false() {
        Options option = Args.parse(Options.class, "-p 80 -d /usr");
        assertFalse(option.logging());
    }

    @Test
    public void test_parse_single_empty_port_default() {
        IntegerOption option = Args.parse(IntegerOption.class);
        assertEquals(0, option.port());
    }

    @Test
    public void test_parse_more_empty_port_default() {
        Options option = Args.parse(Options.class, "-l -d /usr");
        assertEquals(0, option.port());
    }

    @Test
    public void test_parse_single_empty_dir_default() {
        StringOption option = Args.parse(StringOption.class);
        assertEquals("/home/logs", option.directory());
    }

    @Test
    public void test_parse_more_empty_dir_default() {
        Options option = Args.parse(Options.class, "-l -p 80");
        assertEquals("/home/logs", option.directory());
    }
}
