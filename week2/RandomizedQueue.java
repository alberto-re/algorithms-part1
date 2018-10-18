import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int START_SIZE = 1;

    private Item[] array;
    private int size = 0;

    public RandomizedQueue() {
        array = (Item[]) new Object[START_SIZE];
    }

    public static void main(String[] args) {
        // comment needed by the grader
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        array[size] = item;
        size += 1;
        if (size == array.length) {
            Item[] oldArray = array;
            array = (Item[]) new Object[oldArray.length * 2];
            for (int i = 0; i < oldArray.length; i++) {
                array[i] = oldArray[i];
            }
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int rndIndex = StdRandom.uniform(0, size);
        Object retVal = array[rndIndex];
        array[rndIndex] = array[size - 1];
        array[size - 1] = null;
        size -= 1;
        if (array.length >= 4 && size <= array.length / 4) {
            Item[] oldArray = array;
            array = (Item[]) new Object[oldArray.length / 2];
            for (int i = 0; i < size; i++) {
                array[i] = oldArray[i];
            }
        }
        return (Item) retVal;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int rndIndex = StdRandom.uniform(0, size);
        return array[rndIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final int[] order;
        private int curpos;

        public RandomizedQueueIterator() {
            order = StdRandom.permutation(size);
            curpos = 0;
        }

        public boolean hasNext() {
            return curpos < size;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = array[order[curpos]];
            curpos += 1;
            return item;
        }

    }

}