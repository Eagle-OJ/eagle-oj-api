package org.inlighting.oj.web;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author Smith
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        new A();
    }

}

class A {
    private int a = 5;

    public A() {
        System.out.println(a);
    }
}
