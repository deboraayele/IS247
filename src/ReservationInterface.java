import java.util.Scanner;

public interface ReservationInterface {  // Requirement 7: Interface
    void bookRoom(Scanner scanner);
    void cancelReservation(Scanner scanner);
    void viewBookingHistory();
}