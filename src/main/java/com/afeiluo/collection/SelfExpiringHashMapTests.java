package com.afeiluo.collection;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Pierantonio Cangianiello
 */
public class SelfExpiringHashMapTests {

    private final static int SLEEP_MULTIPLIER = 10;

    @Test
    public void basicGetTest() throws InterruptedException {
        SelfExpiringMap<String, String> map = new SelfExpiringHashMap<String, String>();
        map.put("a", "b", 2 * SLEEP_MULTIPLIER);
        Thread.sleep(1 * SLEEP_MULTIPLIER);
        assertEquals(map.get("a"), "b");
    }

    @Test
    public void basicExpireTest() throws InterruptedException {
        SelfExpiringMap<String, String> map = new SelfExpiringHashMap<String, String>();
        map.put("a", "b", 2 * SLEEP_MULTIPLIER);
        Thread.sleep(3 * SLEEP_MULTIPLIER);
        assertNull(map.get("a"));
    }

    @Test
    public void basicRenewTest() throws InterruptedException {
        SelfExpiringMap<String, String> map = new SelfExpiringHashMap<String, String>();
        map.put("a", "b", 3 * SLEEP_MULTIPLIER);
        Thread.sleep(2 * SLEEP_MULTIPLIER);
        map.renewKey("a");
        Thread.sleep(2 * SLEEP_MULTIPLIER);
        assertEquals(map.get("a"), "b");
    }

    @Test
    public void getRenewTest() throws InterruptedException {
        SelfExpiringMap<String, String> map = new SelfExpiringHashMap<String, String>();
        map.put("a", "b", 3 * SLEEP_MULTIPLIER);
        Thread.sleep(2 * SLEEP_MULTIPLIER);
        assertEquals(map.get("a"), "b");
        Thread.sleep(2 * SLEEP_MULTIPLIER);
        assertEquals(map.get("a"), "b");
    }

    @Test
    public void multiplePutThenRemoveTest() throws InterruptedException {
        SelfExpiringMap<String, String> map = new SelfExpiringHashMap<String, String>();
        map.put("a", "b", 2 * SLEEP_MULTIPLIER);
        Thread.sleep(1 * SLEEP_MULTIPLIER);
        map.put("a", "c", 2 * SLEEP_MULTIPLIER);
        Thread.sleep(1 * SLEEP_MULTIPLIER);
        map.put("a", "d", 400 * SLEEP_MULTIPLIER);
        Thread.sleep(2 * SLEEP_MULTIPLIER);
        assertEquals(map.remove("a"), "d");
    }

    @Test
    public void multiplePutThenGetTest() throws InterruptedException {
        SelfExpiringMap<String, String> map = new SelfExpiringHashMap<String, String>();
        map.put("a", "b", 2 * SLEEP_MULTIPLIER);
        Thread.sleep(1 * SLEEP_MULTIPLIER);
        map.put("a", "c", 2 * SLEEP_MULTIPLIER);
        Thread.sleep(1 * SLEEP_MULTIPLIER);
        map.put("a", "d", 400 * SLEEP_MULTIPLIER);
        Thread.sleep(2 * SLEEP_MULTIPLIER);
        assertEquals(map.get("a"), "d");
    }

}
