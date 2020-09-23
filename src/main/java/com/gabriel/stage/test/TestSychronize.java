package com.gabriel.stage.test;

/**
 * @author: Sam.yang
 * @date: 2020/9/21 16:12
 * @desc: 测试同步锁字节码
 */
public class TestSychronize {
    String str = "Welcome";

    private int x = 5;

    public static Integer in = 10;

    public static void main(String[] args) {
        TestSychronize myTest2 = new TestSychronize();

        myTest2.setX(8);

        in = 20;
    }

    private void setX(int x) {
        this.x = x;
    }
}
