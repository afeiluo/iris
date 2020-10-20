package com.afeiluo.jdk_serialization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MyExperiments {
    enum Example1 {
        A(101);
        final int x;

        Example1(int x) {
            this.x = x;
        }

        @JsonValue
        public int code() {
            return x;
        }
    }

    enum Example2 {
        A(202);
        @JsonValue
        public final int x;

        Example2(int x) {
            this.x = x;
        }
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper m = new ObjectMapper();
        m.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        String s1 = m.writer().writeValueAsString(Example1.A);
        String s2 = m.writer().writeValueAsString(Example2.A);

        System.out.println("1: " + s1);
        System.out.println("2: " + s2);

        Example1 e1 = m.readValue(s1, Example1.class);
        Example2 e2 = m.readValue(s2, Example2.class);

        System.out.println("1: " + e1);
        System.out.println("2: " + e2);

        assert e1 == Example1.A : e1;
        assert e2 == Example2.A : e2;
    }
}