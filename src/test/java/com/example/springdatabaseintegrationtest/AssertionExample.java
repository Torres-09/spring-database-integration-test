package com.example.springdatabaseintegrationtest;

import org.junit.jupiter.api.Test;

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
}

