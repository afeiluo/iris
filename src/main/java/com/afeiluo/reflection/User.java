package com.afeiluo.reflection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 16/9/22.
 */
public class User extends People {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String name;

    private int age;

    public static void main(String[] args) {
        //List<User> peopleList = new TArrayList<User>();
    }
}
