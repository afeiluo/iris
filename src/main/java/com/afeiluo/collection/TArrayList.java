package com.afeiluo.collection;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by qiaolinfei on 2020/4/9.
 */
public class TArrayList {

    public static int getCapacity(ArrayList<String> arrayList) {
        Class listClass = arrayList.getClass();
        try {
            Field field = listClass.getDeclaredField("elementData");
            field.setAccessible(true);
            return ((Object[]) field.get(arrayList)).length;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        //向上转型
        ArrayList<String> strList = new ArrayList<String>();
        System.out.println("capacity: " + getCapacity(strList) + " size: " + strList.size());
        strList.add("1");
        System.out.println("capacity: " + getCapacity(strList) + " size: " + strList.size());
        strList = new ArrayList<>(15);
        System.out.println("capacity: " + getCapacity(strList) + " size: " + strList.size());

        System.out.println("=========================================");

        strList = new ArrayList<String>();
        for (int i=0; i<30;i++){
            strList.add(String.valueOf(i));
            System.out.println("capacity: " + getCapacity(strList) + " size: " + strList.size());
        }
    }
}
