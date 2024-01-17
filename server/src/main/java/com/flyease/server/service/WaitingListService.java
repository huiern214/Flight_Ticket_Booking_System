package com.flyease.server.service;

import java.util.List;

public interface WaitingListService {
    List<WaitingListEntry> getAllEntries();

    WaitingListEntry getEntryById(int waitingListId);

    void addEntry(WaitingListEntry entry);

    void removeEntry(int waitingListId);
}
