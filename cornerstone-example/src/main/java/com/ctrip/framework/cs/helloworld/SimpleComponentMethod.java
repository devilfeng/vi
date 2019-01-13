package com.ctrip.framework.cs.helloworld;

import com.ctrip.framework.cs.annotation.ComponentStatus;

/**
 * Created by jiang.j on 2016/6/20.
 */

@ComponentStatus(id = "example.simplecomponentmethod", name = "simple component method", description = "执行组件状态方法示例", custom = true)
public class SimpleComponentMethod {

    public static String hello() {
        return "hello world!";
    }

    public static String hello(HelloReq req) {
        return "hello " + req.name;
    }

    public static void doSome(SomeReq req) throws Exception {

        if (req.hasError) {
            throw new Exception("example for error handle");
        }
    }

    public static class HelloReq {
        public String name;
    }

    public static class SomeReq {
        public boolean hasError;
    }
}
