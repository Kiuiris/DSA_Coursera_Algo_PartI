package Queues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;

        Node() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }

        Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node(item);

        if (size == 0) {
            first = newNode;
            last = first;
        } else {
            Node temp = first;
            first = newNode;
            first.next = temp;
            first.prev = null;
            temp.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node newNode = new Node(item);

        if (size == 0) {
            last = newNode;
            first = last;
        } else {
            Node temp = last;
            last = newNode;
            last.next = null;
            last.prev = temp;
            temp.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) throw new java.util.NoSuchElementException();
        Node temp = first;
        if (size == 1) {
            first = first.next;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return temp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) throw new java.util.NoSuchElementException();
        Node temp = last;
        if (size == 1) {
            last = last.prev;
            first = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return temp.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new dequeIterator();
    }

    private class dequeIterator implements Iterator<Item> {
        private Node cur = first;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item temp = cur.item;
            cur = cur.next;
            return temp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> test = new Deque<>();
        StdOut.println("check if empty b4 adding: " + test.isEmpty());
        StdOut.println("add B");
        test.addFirst("B");
        StdOut.println("check if empty after adding B: " + test.isEmpty());
        StdOut.println("check size after adding 1 item: " + test.size());
        StdOut.println("add A b4 B and C after B");
        test.addFirst("A");
        test.addLast("C");
        StdOut.print("expecting A B C: ");
        for (String s: test) {
            StdOut.print(s + " ");
        }
        StdOut.println();
        StdOut.println("Remove first and last");
        StdOut.print("expecting A: ");
        StdOut.println(test.removeFirst());
        StdOut.print("expecting C: ");
        StdOut.println(test.removeLast());

        test.addLast("C");
        test.addLast("D");
        test.addLast("E");
        test.addLast("F");
        test.addLast("U");
        Iterator<String> iterator = test.iterator();
        StdOut.print("expecting B C D E F U: ");
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }
    }
}
