package org.vertexarmy.dsr.collection;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * created by Alex
 * on 3/9/2015.
 */
public class CircularDoublyLinkedListTests {
    private CircularDoublyLinkedList<Integer> intList;

    @BeforeMethod
    public void setUp() {
        intList = new CircularDoublyLinkedList<>();
    }

    @AfterMethod
    public void tearDown() {
        intList = null;
    }

    @Test
    public void testSimpleInsertions() {
        Assert.assertEquals(intList.size(), 0);

        intList.add(10);
        intList.add(20);
        intList.add(30);
        Assert.assertEquals(intList.size(), 3);
    }

    @Test
    public void testRemoveExistingItem() {
        intList.add(20);
        intList.add(30);
        Assert.assertEquals(intList.size(), 2);

        intList.remove(20);
        Assert.assertEquals(intList.size(), 1);
    }

    @Test
    public void testRemoveNonExistingItem() {
        intList.add(20);
        intList.add(30);
        Assert.assertEquals(intList.size(), 2);

        intList.remove(50);
        Assert.assertEquals(intList.size(), 2);
    }

    @Test
    public void removeSingleElement() {
        intList.add(69);
        intList.remove(69);

        Assert.assertEquals(intList.size(), 0);
    }

    @Test
    public void simpleIterator() {
        intList.add(20);
        intList.add(30);

        CircularDoublyLinkedList.Iterator iterator = intList.getIterator();

        Assert.assertEquals(iterator.current(), 20);
        Assert.assertEquals(iterator.next(), 30);
        Assert.assertEquals(iterator.previous(), 30);
    }

    @Test
    public void forwardIterator() {
        int[] integers = new int[]{20, 32, 45, 43, 153, 43, 513, 431, 654, 100};

        for (int i : integers) {
            intList.add(i);
        }

        CircularDoublyLinkedList.Iterator iterator = intList.getIterator();

        for (int integer : integers) {
            Assert.assertEquals(iterator.current(), integer);
            iterator.forward();
        }
    }

    @Test
    public void backwardIterator() {
        int[] integers = new int[]{20, 32, 45, 43, 153, 43, 513, 431, 654, 100};

        for (int i : integers) {
            intList.add(i);
        }

        CircularDoublyLinkedList.Iterator iterator = intList.getIterator();
        iterator.backward();

        for (int i = integers.length - 1; i >= 0; i--) {
            Assert.assertEquals(iterator.current(), integers[i]);
            iterator.backward();
        }
    }

    @Test
    public void equals() {
        // TODO: implement
    }
}


