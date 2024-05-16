package com;

/**
 * 模拟要被增强的业务类
 */
public class Test {
    public int sum(int a, int b) {
        return a + b;
    }

    public void sayHello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println("invoke sum method:");
        int a = 5, b = 4;
        System.out.println(a + " + " + b + " = " + test.sum(a, b));

        System.out.println("invoke sayHello method:");
        String name = "John";
        test.sayHello(name);
    }
}
