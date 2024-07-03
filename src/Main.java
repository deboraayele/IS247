import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel ("247 Hotel");

        while (true){
            System.out.println("1. Book Room");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. View Booking History");
            System.out.println("4. View all rooms");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            //Check if input is an integer
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Clear invalid input
                System.out.print("Choose an option: ");
            }

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    hotel.bookRoom(scanner);
                    break;
                case 2:
                    hotel.cancelReservation(scanner);
                    break;
                case 3:
                    hotel.viewBookingHistory();
                    break;
                case 4:
                    hotel.printAllRooms();
                    break;
                case 5:
                    System.out.println("Thank you for using the Hotel Reservation System.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}