package org.inlighting.oj.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Smith
 **/
public class Main {
    private static Map<Character, Character> mapping = new HashMap<>();

    static  {
        mapping.put('a', 'v');
        mapping.put('b', 'f');
        mapping.put('c', 'x');
        mapping.put('d', 'e');
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(encode(sc.nextLine()));

    }

    public static String encode(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c: s.toCharArray()) {
            stringBuilder.append(mapping.get(c));
        }
        return stringBuilder.toString();
    }
}
