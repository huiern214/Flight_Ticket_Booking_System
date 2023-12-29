package com.flyease.server.service;

import java.util.LinkedList;

public class WaitingListQueue {
    private LinkedList<WaitingListEntry> queue = new LinkedList<>();

    // Implement methods to add, remove, and view entries in the queue
    public void enqueue(WaitingListEntry entry) {
        queue.addLast(entry);
    }

    public WaitingListEntry dequeue() {
        return queue.pollFirst();
    }

    public WaitingListEntry peek() {
        return queue.peekFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
