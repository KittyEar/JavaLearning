package com.gameserver;

public class B {
    public static B t1 = new B();
    public static B t2 = new B();

    public B(){
        
    }
    {
        int a = 0;
        System.out.println("构造块");
    }
    static
    {
        System.out.println("静态块");
    }
    public static void main(String[] args) {
        float a = 0.75f;
        System.out.println((int) (13 * a));
    }
}
class Main1{

}
