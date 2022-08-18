import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Deque<Item> deque;

    // construct an empty randomized queue
    public RandomizedQueue() {
        deque = new Deque<Item>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
        return deque.size();
    }

    // add the item StdRandom.uniform(1, n + 1);
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Passed parameter cann't be Null!");
        }
        int randomNumber = StdRandom.uniform(1, 3);
        if (randomNumber % 2 == 0) {
            deque.addFirst(item);
        } else {
            deque.addLast(item);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (deque.isEmpty()) {
            throw new java.util.NoSuchElementException("RandomnizedQueue object is empty!");
        }
        int randomNumber = StdRandom.uniform(1, 3);
        Item item;
        if (randomNumber % 2 == 0) {
            item = deque.removeFirst();
        } else {
            item = deque.removeLast();
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (deque.isEmpty()) {
            throw new java.util.NoSuchElementException("RandomnizedQueue object is empty!");
        }
        int randomNumber = StdRandom.uniform(1, 3);
        Item item;
        if (randomNumber % 2 == 0) {
            item = deque.removeFirst();
            deque.addFirst(item);
        } else {
            item = deque.removeLast();
            deque.addLast(item);
        }
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return deque.iterator();
    }


    public static void main(String[] args) {

    }
}
