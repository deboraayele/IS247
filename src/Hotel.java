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
            } else {
                System.out.println("The rate for a " + roomType + " room is $" + room.getRate() + " per night.");
            }
        }

        Date startDate = null;
        Date endDate = null;
        while (startDate == null) {
            System.out.print("Enter booking start date (yyyy-MM-dd): ");
            String startDateString = scanner.next();
            try {
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString);
                Date currentDate = new Date();

                // Allow the start date to be the current date or a future date
                if (startDate.before(currentDate) && !isSameDay(startDate, currentDate)) {
                    System.out.println("Error. Enter a date for the future.");
                    startDate = null;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                startDate = null;
            }
        }

        while (endDate == null) {
            System.out.print("Enter booking end date (yyyy-MM-dd): ");
            String endDateString = scanner.next();
            try {
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
                if (endDate.before(startDate)) {
                    System.out.println("Error end date invalid. End date cannot be before start date. Try again.");
                    endDate = null;
                } else {
                    long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
                    long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
                    double totalRate = room.getRate() * (diffInDays + 1); // Include the start date
                    System.out.println("The total rate for your stay from " + new SimpleDateFormat("yyyy-MM-dd").format(startDate) + " to " + new SimpleDateFormat("yyyy-MM-dd").format(endDate) + " is $" + totalRate);

                    Reservation reservation = new Reservation(++reservationCounter, room, customer, startDate, endDate, totalRate);
                    reservations.put(reservationCounter, reservation);
                    bookingHistory.push(reservation);
                    room.setAvailable(false);
                    System.out.println("Room booked successfully from " + new SimpleDateFormat("yyyy-MM-dd").format(startDate) + " to " + new SimpleDateFormat("yyyy-MM-dd").format(endDate) + "!");
                    System.out.println("Your reservation ID is: " + reservationCounter);
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                endDate = null;
            }
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
        System.out.println("Cancelling reservation for " + reservation.getCustomer().getName() + " from " + new SimpleDateFormat("yyyy-MM-dd").format(reservation.getStartDate()) + " to " + new SimpleDateFormat("yyyy-MM-dd").format(reservation.getEndDate()));
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
        Reservation reservation = bookingHistory.get(index);
        System.out.println(reservation);
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

    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
}
