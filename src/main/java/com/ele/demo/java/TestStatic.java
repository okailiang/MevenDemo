package com.ele.demo.java;

/**
 * @author oukailiang
 * @create 2017-09-20 下午7:13
 */


public class TestStatic {
    public static void main(String[] args) {
        System.out.println(InitalizedClass.INITIALIZED_VARIBLE);

    }
}

class InitalizedClass {
    static {
        System.out.println("You have initalized InitalizedClass!");
    }

    //final static 和  static
    //和上面的例子唯一的差异就是此处的变量INITIALIZED_VARIBLE被声明为final
    public  static int INITIALIZED_VARIBLE = 1;

}