package com.alibaba.easyexcel.test.demo.batest;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

public class C extends F{

    @SneakyThrows
    public void sout(String str) {
        Method m = getClass().getSuperclass().getDeclaredMethod("sout", String.class);
        m.setAccessible(true);
        m.invoke(this, str);

        System.out.println("C ===" + str);
    }

    public static void main(String[] args) {
        C c = new C();
        c.sout("hzouhub");
    }
}
