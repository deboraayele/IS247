import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Hotel implements ReservationInterface {
    private String name;
    private ArrayList<Room> rooms;
    private Stack<Reservation> bookingHistory;
    private Map<Integer, Reservation> reservations;
    private static int reservationCounter = 0;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.bookingHistory = new Stack<>();
        this.reservations = new HashMap<>();
        initializeRooms();
    }

    private void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            rooms.add(new Room(i, "Single", 100.0));
            rooms.add(new Room(i + 10, "Double", 150.0));
        }
    }

    @Override
    public void bookRoom(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.next();
        System.out.print("Enter customer email: ");
        String email = scanner.next();
        System.out.print("Enter customer phone number: ");
        String phoneNumber = scanner.next();
        Customer customer = new Customer(name, email, phoneNumber);

        System.out.print("Enter room type (Single/Double): ");
        String roomType = scanner.next();
        Room room = findAvailableRoom(roomType);

        if (room != null) {
            System.out.print("Enter booking date (yyyy-MM-dd): ");
            String dateString = scanner.next();
            Date bookingDate = null;
            try {
                bookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            Reservation reservation = new Reservation(++reservationCounter, room, customer, bookingDate);
            reservations.put(reservationCounter, reservation);
            bookingHistory.push(reservation);
            room.setAvailable(false);
            System.out.println("Room booked successfully for " + bookingDate + "!");
        } else {
            System.out.println("No available rooms of type " + roomType);
        }
    }

    private Room findAvailableRoom(String type) {
        for (Room room : rooms) {
            if (room.getType().equalsIgnoreCase(type) && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }

    @Override
    public void cancelReservation(Scanner scanner) {
        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();

        Reservation reservation = reservations.get(reservationId);
        if (reservation != null) {
            reservation.getRoom().setAvailable(true);
            reservations.remove(reservationId);
            System.out.println("Reservation cancelled successfully!");
        } else {
            System.out.println("Invalid reservation ID.");
        }
    }

    @Override
    public void viewBookingHistory() {
        viewBookingHistoryRecursive(bookingHistory.size() - 1);
    }

    private void viewBookingHistoryRecursive(int index) {
        if (index < 0) return;
        System.out.println(bookingHistory.get(index));
        viewBookingHistoryRecursive(index - 1);
    }
}
