package com.benjamin.objects;

import java.util.*;

public class CircularList<E> extends ArrayList<E> {

    public CircularList() {
        super();
    }

    public CircularList(int initialCapacity) {
        super(initialCapacity);
    }

    public CircularList(Collection<? extends E> collection) {
        super(collection);

    }

    @Override
    public E get(int index) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("The list is empty");
        }

        while (index < 0) {
            index = size() + index;
        }

        return super.get(index % size());
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, this.size());
        return new SubList<>(this, fromIndex, toIndex);
    }

    // TODO modify to my needs
    private static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
    }

    private static class SubList<E> extends AbstractList<E> {
        private final CircularList<E> root;
        private final SubList<E> parent;
        private final int offset;
        private int size;

        /**
         * Constructs a sublist of an arbitrary ArrayList.
         */
        public SubList(CircularList<E> root, int fromIndex, int toIndex) {
            this.root = root;
            this.parent = null;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
        }

        @Override
        public E get(int index) {
            Objects.checkIndex(index, size);
            return root.get(offset + index);
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public E set(int index, E element) {
            return super.set(index, element);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) this.toArray(), 0, this.size(), c);
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
        modCount++;
    }
}