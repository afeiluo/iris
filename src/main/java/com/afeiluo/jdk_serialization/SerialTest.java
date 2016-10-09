package com.afeiluo.jdk_serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by ben on 16/9/28.
 */
public class SerialTest extends Parent implements Serializable {
    int version = 66;
    Contain con = new Contain();

    public int getVersion()

    {
        return version;
    }

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        SerialTest st = new SerialTest();
        oos.writeObject(st);
        oos.flush();
        oos.close();
    }
}

class Parent implements Serializable {
    int parentVersion = 10;
}

class Contain implements Serializable {
    int containVersion = 11;
}

