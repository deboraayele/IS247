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

        String email;
        while (true) {
            System.out.print("Enter customer email: ");
            email = scanner.next();
            if (email.contains("@") && email.endsWith(".com")) {
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        }

        String phoneNumber;
        while (true) {
            System.out.print("Enter customer phone number: ");
            phoneNumber = scanner.next();
            if (isValidPhoneNumber(phoneNumber)) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter a valid 10-digit phone number.");
            }
        }

        Customer customer = new Customer(name, email, phoneNumber);

        Room room = null;
        while (room == null) {
            System.out.print("Enter room type (Single/Double): ");
            String roomType = scanner.next();
            room = findAvailableRoom(roomType);

            if (room == null) {
                System.out.println("No available rooms of type " + roomType + ". Please enter a valid room type.");
            }
        }

        Date bookingDate = null;
        while (bookingDate == null) {
            System.out.print("Enter booking date (yyyy-MM-dd): ");
            String dateString = scanner.next();
            try {
                bookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                if (bookingDate.before(new Date())) {
                    System.out.println("Invalid date. Booking date cannot be in the past. Please enter a valid future date.");
                    bookingDate = null;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        Reservation reservation = new Reservation(++reservationCounter, room, customer, bookingDate);
        reservations.put(reservationCounter, reservation);
        bookingHistory.push(reservation);
        room.setAvailable(false);
        System.out.println("Room booked successfully for " + new SimpleDateFormat("yyyy-MM-dd").format(bookingDate) + "!");
        System.out.println("Your reservation ID is: " + reservationCounter);
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
        int reservationId;
        while (true) {
            System.out.print("Enter reservation ID to cancel: ");
            if (scanner.hasNextInt()) {
                reservationId = scanner.nextInt();
                if (reservations.containsKey(reservationId)) {
                    break;
                } else {
                    System.out.println("Invalid reservation ID. Please provide a valid reservation ID.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid reservation ID.");
                scanner.next(); // Clear invalid input
            }
        }

        Reservation reservation = reservations.get(reservationId);
        reservation.getRoom().setAvailable(true);
        reservations.remove(reservationId);
        System.out.println("Reservation cancelled successfully!");
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

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            return false;
        }
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
