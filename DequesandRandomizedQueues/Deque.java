import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first, last;

    private class Node {
        Item value;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("passed Item can't be Null!");
        }
        Node oldFirst = first;
        first = new Node();
        first.next = oldFirst;
        first.value = item;

        if (last == null) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("passed Item can't be Null!");
        }
        Node oldLast = last;
        last = new Node();
        last.value = item;
        last.next = null;
        if (first == null) first = last;
        else oldLast.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Empty Deque!");
        }
        if (size == 1) {
            Item item = first.value;
            first = null;
            last = null;
            size = 0;
            return item;
        } else {
            Item item = first.value;
            Node oldFirst = first;
            first = first.next;
            size--;
            oldFirst = null;
            return item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Empty Deque!");
        }
        Item item = last.value;
        if (size == 1) {

            first = null;
            last = null;
        } else {
            Node newLast = first;
            while (newLast.next.next != null) {
                newLast = newLast.next;
            }
            last = newLast;
            last.next = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupporsted Operation");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("Iterator equals Null!");
            }
            Item item = current.value;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {

    }
}
