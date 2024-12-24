package project3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a sorted doubly-linked list.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow <code>null</code> elements.
 *
 * @author Joanna Klukowska
 * @author Thales Gao
 *
 * @param <E> the type of elements held in this list
 */
public class SortedLinkedList<E extends Comparable<E>>
    implements Iterable<E> {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the list in ascending order.
     *
     * @param element the element to add
     * @return <code>true</code> if the element was added successfully,
     * <code>false</code> otherwise (if <code>element==null</code>)
     */
    public boolean add(E element) {
        if (element == null) {
            return false;
        }
        Node e = new Node(element);
        if (this.head == null) {
            this.head = e;
            this.tail = e;
        } else if (this.head.compareTo(e) >= 0) {
            e.next = this.head;
            this.head.prev = e;
            this.head = e;
        } else {
            Node node = this.head;
            while (node.next != null && node.next.compareTo(e) < 0) {
                node = node.next;
            }
            e.next = node.next;
            if (node.next != null) {
                node.next.prev = e;
            } else {
                tail = e;
            }
            node.next = e;
            e.prev = node;
        }
        size++;
        return true;
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Returns <code>true</code> if the list contains the specified element,
     * <code>false</code> otherwise.
     *
     * @param o the element to search for
     * @return <code>true</code> if the element is in the list,
     * <code>false</code> otherwise
     */
    public boolean contains(Object o) {
        if(o == null) {
            return false;
        }
        Iterator itr = this.iterator();
        while(itr.hasNext()) {
            if(o.equals(itr.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException  if the index is out of
     * range <code>(index < 0 || index >= size())</code>
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("index not in range");
        }
        Node node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.data;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the element is not in the list.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element,
     * or -1 if the element is not in the list
     */
    public int indexOf(Object o) {
        return this.nextIndexOf(o, 0);
    } 

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * starting at the specified <code>index</code>, i.e., in the range of indexes
     * <code>index <= i < size()</code>, or -1 if the element is not in the list
     * in the range of indexes <code>index <= i < size()</code>.
     *
     * @param o the element to search for
     * @param index the index to start searching from
     * @return the index of the first occurrence of the element, starting at the specified index,
     * or -1 if the element is not found
     */
    public int nextIndexOf(Object o, int index) {
        if(o == null || !this.contains(o) || index < 0 || index >= this.size) {
            return -1;
        }
        Iterator itr = this.iterator();
        int i;
        for (i = 0; i < index; i++) {
            itr.next();
        }
        while (itr.hasNext()) {
            if (o.equals(itr.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param o the element to remove
     * @return <code>true</code> if the element was removed successfully,
     * <code>false</code> otherwise
     */
    public boolean remove(Object o) {
        if(!this.contains(o)) {
            return false;
        }
        int i = this.indexOf(o);
        if (i == 0) {
            this.head = this.head.next;
            this.head.prev.next = null;
            this.head.prev = null;
            this.size--;
            return true;
        } else if (i == size - 1) {
            this.tail = this.tail.prev;
            this.tail.next.prev = null;
            this.tail.next = null;
            this.size--;
            return true;
        } else {
            Node node = this.head;
            for (int j = 0; j < i; j++) {
                node = node.next;
            }
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
            this.size--;
            return true;
        }
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Compares the specified object with this list for equality.
     *
     * @param o the object to compare with
     * @return <code>true</code> if the specified object is equal to this list,
     * <code>false</code> otherwise
     */
    public boolean equals(Object o) {
        if (o == null || !(o instanceof SortedLinkedList) || this.size() != ((SortedLinkedList) o).size()) {
            return false;
        }
        Iterator itr = this.iterator();
        Iterator oitr = ((SortedLinkedList) o).iterator();
        while (itr.hasNext()) {
            if (!itr.next().equals(oitr.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the list.
     *  The string representation consists of a list of the lists's elements in
     *  ascending order, enclosed in square brackets ("[]").
     *  Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */
    public String toString() {
        String str = "[";
        if (this.head == null || this.tail == null){
            return "[]";
        }
        Node node = this.head;
        for (int i = 0; i < this.size; i++){
            str = str + node.data.toString() + ", ";
            node = node.next;
        }
        str = str.substring(0, str.length() - 2) + "]";
        return str;
    }

    /* Inner class to represent nodes of this list.*/
    private class Node implements Comparable<Node> {
        E data;
        Node next;
        Node prev;
        Node(E data) {
            if (data == null ) throw new NullPointerException ("does not allow null");
            this.data = data;
        }
        Node (E data, Node next, Node prev) {
            this(data);
            this.next = next;
            this.prev = prev;
        }
        public int compareTo( Node n ) {
            return this.data.compareTo(n.data);
        }
    }

    /* A basic forward iterator for this list. */
    private class ListIterator implements Iterator<E> {

        Node nextToReturn = head;
        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (nextToReturn == null )
                throw new NoSuchElementException("the end of the list reached");
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next;
            return tmp;
        }

    }
}
