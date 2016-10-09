package com.afeiluo.jdk_serialization;

import java.io.Serializable;

/**
 * Created by ben on 16/9/27.
 */
public class Animal implements Serializable {
    private String name;
    private House preferredHouse;

    Animal(String nm, House h) {
        name = nm;
        preferredHouse = h;
    }

    public String toString() {
        return name + "[" + super.toString() + "]," + preferredHouse + "\n";
    }


}
