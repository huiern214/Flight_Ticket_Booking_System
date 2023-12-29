package com.flyease.server.controller;

import com.flyease.server.service.WaitingListEntry;
import com.flyease.server.service.WaitingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waiting-list")
public class WaitingListController {

    @Autowired
    private WaitingListService waitingListService;

    @GetMapping
    public List<WaitingListEntry> getAllEntries() {
        // Implement logic to return all waiting list entries
        return waitingListService.getAllEntries();
    }

    @PostMapping
    public ResponseEntity<String> addEntry(@RequestBody WaitingListEntry entry) {
        // Implement logic to add a new entry
        waitingListService.addEntry(entry);
        return ResponseEntity.ok("Entry added successfully.");
    }

    @DeleteMapping("/{waitingListId}")
    public ResponseEntity<String> removeEntry(@PathVariable int waitingListId) {
        // Implement logic to remove an entry by ID
        waitingListService.removeEntry(waitingListId);
        return ResponseEntity.ok("Entry removed successfully.");
    }
}
