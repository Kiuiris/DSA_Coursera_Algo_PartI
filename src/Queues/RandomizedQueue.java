package Queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue(){
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == items.length) {
            resizeItems(size * 2);
        }
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(0, size);
        Item temp = items[randIndex];
        for (int i = randIndex; i < size - 1; i++) {
            if (i == size - 1) items[i] = null;
            items[i] = items[i + 1];
        }
        size--;
        if (size > 0 && size == items.length/4) {
            resizeItems(items.length/2);
        }
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(0, size);
        return items[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new randomQueueIterator();
    }

    private class randomQueueIterator implements Iterator<Item> {
        int curIndex = StdRandom.uniform(0, size);
        Item[] copy = items;
        int copySize = size;

        @Override
        public boolean hasNext() {
            return copySize > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item temp = copy[curIndex];
            for (int i = curIndex; i < copySize - 1; i++) {
                if (i == copySize - 1) copy[i] = null;
                copy[i] = copy[i + 1];
            }
            copySize--;
            if (copySize != 0) curIndex = StdRandom.uniform(0, copySize);
            return temp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resizeItems(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<>();
        StdOut.println("check if empty b4 adding: " + test.isEmpty());
        StdOut.println("add B");
        test.enqueue("B");
        StdOut.println("check if empty after adding B: " + test.isEmpty());
        StdOut.println("check size after adding 1 item: " + test.size());
        StdOut.println("add A and C");
        test.enqueue("A");
        test.enqueue("C");
        StdOut.println("remove a random item: " + test.dequeue());
        StdOut.println("check size after removing an item: " + test.size());
        StdOut.println("remove another random item: " + test.dequeue());

        test.enqueue("C");
        test.enqueue("D");
        test.enqueue("E");
        test.enqueue("F");
        test.enqueue("U");
        Iterator<String> iterator = test.iterator();
        StdOut.print("random queue: ");
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
    }
}
