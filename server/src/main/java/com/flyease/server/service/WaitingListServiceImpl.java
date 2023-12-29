package com.flyease.server.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WaitingListServiceImpl implements WaitingListService {
    private List<WaitingListEntry> waitingListEntries;

    public WaitingListServiceImpl() {
        // Initialize your list or any other necessary setup
        waitingListEntries = new ArrayList<>();
    }

    @Override
    public List<WaitingListEntry> getAllEntries() {
        return waitingListEntries;
    }

    @Override
    public WaitingListEntry getEntryById(int waitingListId) {
        for (WaitingListEntry entry : waitingListEntries) {
            if (entry.getWaitingListId() == waitingListId) {
                return entry;
            }
        }
        return null; // or throw an exception
    }

    @Override
    public void addEntry(WaitingListEntry entry) {
        waitingListEntries.add(entry);
    }

    @Override
    public void removeEntry(int waitingListId) {
        Iterator<WaitingListEntry> iterator = waitingListEntries.iterator();
        while (iterator.hasNext()) {
            WaitingListEntry entry = iterator.next();
            if (entry.getWaitingListId() == waitingListId) {
                iterator.remove();
                return;
            }
        }
        // Entry not found, you might want to throw an exception
    }
}
