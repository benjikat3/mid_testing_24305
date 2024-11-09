package com.ben.mid_term.util;

import com.ben.mid_term.dao.RoomDao;
import com.ben.mid_term.dao.ShelfDao;
import com.ben.mid_term.model.Room;
import com.ben.mid_term.model.Shelf;

public class RoomShelfPopulator {

    private static RoomDao roomDao = new RoomDao();
    private static ShelfDao shelfDao = new ShelfDao();

    public static void populateRoomShelfData() {
        // Create or retrieve two rooms
        Room room1 = createOrGetRoom("R101", 100);
        Room room2 = createOrGetRoom("R102", 80);

        // Create two shelves in room1
        createShelf(10, 15, 5, "Fiction", room1);
        createShelf(7, 10, 3, "Science", room1);

        // Create two shelves in room2
        createShelf(5, 5, 0, "History", room2);
        createShelf(12, 15, 3, "Technology", room2);

        System.out.println("2 Rooms and 2 Shelves per room created successfully!");
    }

    // Helper method to create or get an existing room by room code
    private static Room createOrGetRoom(String roomCode, int capacity) {
        Room room = roomDao.findByRoomCode(roomCode);
        if (room == null) {
            room = new Room();
            room.setRoomCode(roomCode);
            room.setCapacity(capacity);
            roomDao.insert(room);
            System.out.println("Room created: " + roomCode);
        } else {
            System.out.println("Room already exists: " + roomCode);
        }
        return room;
    }

    // Helper method to create a shelf
    private static void createShelf(int availableStock, int initialStock, int borrowedNumber, String category, Room room) {
        Shelf shelf = new Shelf();
        shelf.setAvailableStock(availableStock);
        shelf.setInitialStock(initialStock);
        shelf.setBorrowedNumber(borrowedNumber);
        shelf.setBookCategory(category);
        shelf.setRoom(room);
        shelfDao.insert(shelf);
        System.out.println("Shelf created in room " + room.getRoomCode() + " with category " + category);
    }
}
