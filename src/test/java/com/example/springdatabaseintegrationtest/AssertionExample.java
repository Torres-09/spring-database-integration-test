package com.example.springdatabaseintegrationtest;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionExample {
    @Test
    void test(){
        String str1 = "hello";
        String str2 = new String("hello");
        String str3 = str1;

        assertEquals(str1, str3); // 내용이 같으므로 통과
        assertNotSame(str1, str2);    // 서로 다른 객체이므로 실패

        assertSame(str1, str3);    // 같은 객체를 참조하므로 통과
    }

    @Test
    void test2() {
        TestClass testClass1 = new TestClass(1, 2);
        TestClass testClass2 = new TestClass(1, 2);

        assertEquals(testClass1, testClass2,"맞왜틀?");
        assertNotSame(testClass1, testClass2);
    }

    static class TestClass {
        int x;
        int y;

        public TestClass(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @ParameterizedTest
    @EnumSource(Month.class) // passing all 12 months
    @DisplayName("Month test")
    void getValueForAMonth_IsAlwaysBetweenOneAndTwelve(Month month) {
        int monthNumber = month.getValue();
        assertTrue(monthNumber >= 1 && monthNumber <= 12);
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
    @DisplayName("Month test")
    void someMonths_Are30DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertEquals(30, month.length(isALeapYear));
    }

    @ParameterizedTest
    @EnumSource(
            value = Month.class,
            names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"},
            mode = EnumSource.Mode.EXCLUDE)
    void exceptFourMonths_OthersAre31DaysLong(Month month) {
        final boolean isALeapYear = false;
        assertEquals(31, month.length(isALeapYear));
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank") // needs to match an existing method.
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
        assertEquals(expected, Strings.isBlank(input));
    }

    // a static method that returns a Stream of Arguments
    private static Stream<Arguments> provideStringsForIsBlank() { // argument source method
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }

    @ParameterizedTest
    @MethodSource("com.example.springdatabaseintegrationtest.StringParams#blankStrings") // 클래스 외부의 source method
    void isBlank_ShouldReturnTrueForNullOrBlankStringsExternalSource(String input) {
        assertTrue(Strings.isBlank(input));
    }
}


