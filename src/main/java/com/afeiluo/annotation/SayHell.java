package com.afeiluo.annotation;

/**
 * Created by ben on 16/10/9.
 */
@Yts(classType = Yts.YtsType.entity)
public class SayHell {

    @HelloWorld(name = "小明")
    @Yts
    public void sayHello(String name) {
        if (name == null || name.equals("")) {
            System.out.println("hello world!");
        } else {
            System.out.println(name + " say hello world");
        }
    }

}
