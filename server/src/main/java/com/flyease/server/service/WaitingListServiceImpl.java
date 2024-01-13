package com.flyease.server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class WaitingListServiceImpl implements WaitingListService {

    private Connection connection;

    public WaitingListServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<WaitingListEntry> getAllEntries() {
        List<WaitingListEntry> entries = new ArrayList<>();
        String query = "SELECT * FROM Waiting_List";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                WaitingListEntry entry = extractEntryFromResultSet(resultSet);
                entries.add(entry);
            }

        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

        return entries;
    }

    @Override
    public WaitingListEntry getEntryById(int waitingListId) {
        String query = "SELECT * FROM Waiting_List WHERE waiting_list_id = ?";
        WaitingListEntry entry = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, waitingListId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    entry = extractEntryFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

        return entry;
    }

    @Override
    public void addEntry(WaitingListEntry entry) {
        String query = "INSERT INTO Waiting_List (flight_id, passenger_id, status, date_added) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, entry.getFlightId());
            statement.setInt(2, entry.getPassengerId());
            statement.setString(3, entry.getStatus());
            statement.setTimestamp(4, entry.getDateAdded());

            statement.executeUpdate();

        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
    }

    @Override
    public void removeEntry(int waitingListId) {
        String query = "DELETE FROM Waiting_List WHERE waiting_list_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, waitingListId);
            statement.executeUpdate();

        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
    }

    private WaitingListEntry extractEntryFromResultSet(ResultSet resultSet) throws SQLException {
        int waitingListId = resultSet.getInt("waiting_list_id");
        int flightId = resultSet
                .getInt("flight_id");
        int passengerId = resultSet.getInt("passenger_id");
        String status = resultSet.getString("status");
        Timestamp dateAdded = resultSet.getTimestamp("date_added");

        return new WaitingListEntry(waitingListId, flightId, passengerId, status, dateAdded);
    }
}
