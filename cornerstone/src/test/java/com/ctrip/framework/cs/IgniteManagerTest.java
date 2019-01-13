package com.ctrip.framework.cs;

import org.junit.Test;

/**
 * Created by jiang.j on 2016/8/11.
 */
public class IgniteManagerTest {
    @Test
    public void testRegister() {
        System.out.println("nim");
        MyTest.hell();
        MyTest.hell();
    }

    public static class MyTest {
        static {
            System.out.println("hello");
        }

        public static void hell() {
            System.out.println("my hello");
        }
    }
}
