package com.flyease.server.service;

import java.util.Comparator;

public class WaitingListQueue {
    // Using a priority queue based on the datetime
    private java.util.PriorityQueue<WaitingListEntry> queue = new java.util.PriorityQueue<>(
            Comparator.comparing(WaitingListEntry::getDateAdded));

    // Implement methods to add, remove, and view entries in the queue
    public void enqueue(WaitingListEntry entry) {
        queue.offer(entry);
    }

    public WaitingListEntry dequeue() {
        return queue.poll();
    }

    public WaitingListEntry peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
