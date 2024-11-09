package com.ben.mid_term.util;

import com.ben.mid_term.dao.RoomDao;
import com.ben.mid_term.dao.ShelfDao;
import com.ben.mid_term.model.Room;
import com.ben.mid_term.model.Shelf;

import java.util.UUID;

public class RoomShelfPopulator {

    private static RoomDao roomDao = new RoomDao();
    private static ShelfDao shelfDao = new ShelfDao();

    public static void populateRoomShelfData() {
        // Create two rooms
        Room room1 = new Room();
        room1.setRoomCode("R101");
        room1.setCapacity(100);
        roomDao.insert(room1);

        Room room2 = new Room();
        room2.setRoomCode("R102");
        room2.setCapacity(80);
        roomDao.insert(room2);

        // Create four shelves with different book categories in each room
        Shelf shelf1 = new Shelf();
        shelf1.setAvailableStock(10);
        shelf1.setInitialStock(15);
        shelf1.setBorrowedNumber(5);
        shelf1.setBookCategory("Fiction");
        shelf1.setRoom(room1);
        shelfDao.insert(shelf1);

        Shelf shelf2 = new Shelf();
        shelf2.setAvailableStock(7);
        shelf2.setInitialStock(10);
        shelf2.setBorrowedNumber(3);
        shelf2.setBookCategory("Science");
        shelf2.setRoom(room1);
        shelfDao.insert(shelf2);

        Shelf shelf3 = new Shelf();
        shelf3.setAvailableStock(5);
        shelf3.setInitialStock(5);
        shelf3.setBorrowedNumber(0);
        shelf3.setBookCategory("History");
        shelf3.setRoom(room2);
        shelfDao.insert(shelf3);

        Shelf shelf4 = new Shelf();
        shelf4.setAvailableStock(12);
        shelf4.setInitialStock(15);
        shelf4.setBorrowedNumber(3);
        shelf4.setBookCategory("Technology");
        shelf4.setRoom(room2);
        shelfDao.insert(shelf4);

        System.out.println("Rooms and shelves populated successfully!");
    }
}
