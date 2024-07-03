import java.util.ArrayList; // Requirement 13: ArrayList
import java.util.HashMap; // Requirement 15: Map
import java.util.Map; // Requirement 15: Map
import java.util.Stack; // Requirement 14: Stacks
import java.util.Scanner; // Requirement 1: Scanner
import java.util.Date; // Requirement 21: Java Library class
import java.text.SimpleDateFormat; // Requirement 21: Java Library class
import java.text.ParseException; // Requirement 9: Exception

public class Hotel implements ReservationInterface { // Requirement 7: Interface
    private String name; // Requirement 2: Variables
    private RoomList<Room> rooms; // Using generics for RoomList
    private Stack<Reservation> bookingHistory; // Requirement 14: Stacks
    private Map<Integer, Reservation> reservations; // Requirement 15: Map
    private static int reservationCounter = 0; // Requirement 22: Static variable

    public Hotel(String name) { // Requirement 16: Constructor
        this.name = name;
        this.rooms = new RoomList<>();
        this.bookingHistory = new Stack<>();
        this.reservations = new HashMap<>();
        initializeRooms();
    }

    private void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            rooms.addRoom(new Room(i, "Single", 100.0));  // Requirement 24: Lists
            rooms.addRoom(new Room(i + 10, "Double", 150.0));   // Requirement 24: Lists
        }
    }

    @Override
    public void bookRoom(Scanner scanner) {
        bookRoom(scanner, null);
    }

    public void bookRoom(Scanner scanner, String specialRequest) {
        System.out.print("Enter customer name: ");
        String name = scanner.next(); // Requirement 2: Variables

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

        Customer customer = new Customer(name, email, phoneNumber); // Requirement 6: Inheritance

        Room room = null;
        while (room == null) {
            System.out.print("Enter room type (Single/Double): ");
            String roomType = scanner.next();
            room = rooms.findAvailableRoom(roomType);

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
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateString); // Requirement 21: Java library class
                Date currentDate = new Date(); // Requirement 21: Java library class

                if (startDate.before(currentDate) && !isSameDay(startDate, currentDate)) {
                    throw new InvalidReservationException("Error. Enter a date for the future.");
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                startDate = null;
            } catch (InvalidReservationException e) {
                System.out.println(e.getMessage());
                startDate = null;
            }
        }

        while (endDate == null) {
            System.out.print("Enter booking end date (yyyy-MM-dd): ");
            String endDateString = scanner.next();
            try {
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateString);
                if (endDate.before(startDate)) {
                    throw new InvalidReservationException("Error end date invalid. End date cannot be before start date. Try again.");
                } else {
                    long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
                    long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
                    double totalRate = room.getRate() * (diffInDays + 1); // Include the start date
                    System.out.printf("The total rate for your stay from %s to %s is $%.2f%n", new SimpleDateFormat("yyyy-MM-dd").format(startDate), new SimpleDateFormat("yyyy-MM-dd").format(endDate), totalRate);

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
            } catch (InvalidReservationException e) {
                System.out.println(e.getMessage());
                endDate = null;
            }
        }
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
        reservation.getRoom().setAvailable(true); // Update room availability
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
        System.out.println(reservation); // Requirement 25: println method
        viewBookingHistoryRecursive(index - 1);
    }

    public void printAllRooms() {
        for (Room room : rooms.getRooms()) {
            System.out.println(room);
            System.out.println("Availability: " + room.isAvailable()); // Explicitly using the isAvailable method
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.length() == 10 && phoneNumber.chars().allMatch(Character::isDigit);
    }

    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
}
