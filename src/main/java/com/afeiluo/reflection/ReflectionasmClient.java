package com.afeiluo.reflection;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Method;

/**
 * Created by ben on 16/9/22.
 */
public class ReflectionasmClient {

    public static void main(String[] args) throws Exception {
        testJdkReflect();
        System.out.println();
        testReflectAsm();
    }

    public static void testJdkReflect() throws Exception {
        SomeClass someObject = new SomeClass();
        Method method = SomeClass.class.getMethod("foo", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 1000000000; j++) {
                method.invoke(someObject, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }

    public static void testReflectAsm() {
        SomeClass someObject = new SomeClass();
        //  ConstructorAccess.get(SomeClass.class);
        MethodAccess access = MethodAccess.get(SomeClass.class);
        int fooIndex = access.getIndex("foo", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 1000000000; j++) {
                access.invoke(someObject, fooIndex, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }
}
