package com.flyease.server.model;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsLinkedList {
    private Node head;
    private Node tail;
    private int size;

    public OrderDetailsLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // getSize method to return the size of the linked list
    public int getSize() {
        return size;
    }

    // Method to insert a new OrderDetails object into the linked list
    // The linked list is sorted based on the flight departure date and time
    public void insert(OrderDetails newOrderDetails) {
        Node newNode = new Node(newOrderDetails);
        if (head == null || isNewOrderAfter(newOrderDetails, head.getData())) {
            newNode.setNext(head);
            head = newNode;
            if (tail == null) {
                tail = head;
            }
        } else {
            Node current = head;
            // if the new order is not after the current order, move to the next order
            while (current.next != null && !isNewOrderAfter(newOrderDetails, current.getNext().getData())) {
                current = current.getNext();
            }
            newNode.next = current.getNext();
            current.setNext(newNode);
            if (current == tail) {
                tail = newNode;
            }
        }
        size++;
    }

    private boolean isNewOrderAfter(OrderDetails newOrder, OrderDetails existingOrder) {
        // Compare flight departure dates
        int dateComparison = newOrder.getFlight().getFlightDepartureDate()
                .compareTo(existingOrder.getFlight().getFlightDepartureDate());
        if (dateComparison > 0) {
            return true; // New order's departure date is after existing order's departure date
        } else if (dateComparison < 0) {
            return false; // New order's departure date is before existing order's departure date
        } else {
            // If departure dates are equal, compare departure times
            return newOrder.getFlight().getFlightDepartureTime()
                    .compareTo(existingOrder.getFlight().getFlightDepartureTime()) > 0;
        }
    }

    // return List of OrderDetails in order based on the flight departure date and time
    public List<OrderDetails> getOrderDetailsListByFlightDateTimeOrder() {
        Node current = head;
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        while (current != null) {
            orderDetailsList.add(current.getData());
            current = current.getNext();
        }
        return orderDetailsList;
    }

    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.println(current.getData().getFlight().getFlightArrivalDate());
            current = current.getNext();
        }
    }
}

// Node class to represent each node in the linked list
class Node {
    OrderDetails data;
    Node next;

    public Node() {
        this.data = null;
        this.next = null;
    }

    public Node(OrderDetails data) {
        this.data = data;
        this.next = null;
    }

    public Node(OrderDetails data, Node next) {
        this.data = data;
        this.next = next;
    }

    public OrderDetails getData() {
        return data;
    }

    public void setData(OrderDetails data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}