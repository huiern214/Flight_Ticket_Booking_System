package com.flyease.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WaitingListQueue<T> {
    private List<T> queue = new ArrayList<>();

    public void enqueue(T entry) {
        queue.add(entry);
        // Sort the list based on the datetime after adding a new entry
        if (entry instanceof OrderDetails) {
            Collections.sort(queue, Comparator.comparing(o ->
                ((OrderDetails) o).getOrder().getOrderTimestamp())
            );
        }
    }

    public T dequeue() {
        if (!queue.isEmpty()) {
            return queue.remove(0);
        }
        return null;
    }

    public T peek() {
        return queue.isEmpty() ? null : queue.get(0);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public List<T> getQueue() {
        return queue;
    }

    public void setQueue(List<T> queue) {
        this.queue = queue;
    }

    public void printQueue() {
        for (T entry : queue) {
            System.out.println(entry);
        }
    }
}
