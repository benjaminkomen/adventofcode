package com.benjamin.objects;

import java.util.ArrayList;
import java.util.Collection;

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
}