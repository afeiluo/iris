package com.afeiluo.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by qiaolinfei on 2020/4/1.
 */
public class ArrayToList {


    @Test
    public void testWrongConvert() {
        String[] arr = {"1", "2", "3"};
        List<String> list = Arrays.asList(arr);
        try {
            list.add("4");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException);
        }

        int[] intArr = {1, 2, 3, 4};
        List intList = Arrays.asList(intArr);
        assertTrue(intList.size() == 1);
    }

    @Test
    public void testRightConvert() {
        String[] arr = {"1", "2", "3"};
        List<String> list = new ArrayList<>(Arrays.asList(arr));
        list.add("4");
        assertTrue(list.stream().anyMatch(x -> x.equals("4")));

        list = Arrays.stream(arr).collect(Collectors.toList());
        assertTrue(list.size() == 3);

        int[] intArr = {1, 2, 3};
        List intList = Arrays.stream(intArr).boxed().collect(Collectors.toList());
        assertTrue(intList.size() == 3);
    }
}
