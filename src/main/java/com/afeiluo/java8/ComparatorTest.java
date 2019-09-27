package com.afeiluo.java8;

import org.apache.logging.log4j.util.PropertySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author ben
 * @date 2019/9/27
 */
public class ComparatorTest {
    public static void main(String[] args) {

        // before sort
        List<Order> list = Arrays.asList(
                new Order("A382y482y48", 320),
                new Order("Vvekhfbkje2", 242),
                new Order("efkhfbekjfbe", 1345),
                new Order("bhdhdfaddvad", 230),
                new Order("abkasbcjabjc", 100));
        System.out.println("Before Sort:");
        list.forEach(order -> System.out.println(order));

        Collections.sort(list,
                Comparator.comparingInt(
                        Order::getValue));
        System.out.println("\nAfter Sort:");
        list.forEach(order -> System.out.println(order));
        //获取最大的值
        list.stream().max(Comparator.comparingInt(Order::getValue)).ifPresent(x-> System.out.println(x.getValue()));
    }
}

class Order implements Comparable<Order> {
    public String orderNo;

    public int value;

    public int compareTo(Order o1) {
        return orderNo.compareTo(o1.orderNo);
    }

    public Order(String orderNo, int value) {
        super();
        this.orderNo = orderNo;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Order [orderNo=" + orderNo
                + ", value=" + value + "]";
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
