import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    public Deque() {
    }

    public static void main(String[] args) {
        // comment needed by the grader
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            first = new Node();
            first.value = item;
            last = first;
        } else {
            Node newEl = new Node();
            newEl.value = item;
            newEl.next = first;
            first = newEl;
            first.next.prev = newEl;
        }
        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == 0) {
            first = new Node();
            first.value = item;
            last = first;
        } else {
            Node oldLast = last;
            last = new Node();
            last.value = item;
            oldLast.next = last;
            last.prev = oldLast;
        }
        size += 1;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item retVal = first.value;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        size -= 1;
        return retVal;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item retVal = last.value;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        size -= 1;
        return retVal;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        Item value;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.value;
            current = current.next;
            return item;
        }

    }

}