package com.afeiluo.reflection;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * Created by ben on 16/9/22.
 */
public class ReflectionAsmTest {
    public static void main(String[] args) {
        User user = new User();
        MethodAccess access = MethodAccess.get(User.class);
        access.invoke(user, "setName", "qiao");
        access.invoke(user, "setAge", 26);

        String name = (String) access.invoke(user, "getName", null);
        Integer age = (Integer) access.invoke(user, "getAge", null);
        System.out.println("name:" + name + " age:" + age);
    }
}
