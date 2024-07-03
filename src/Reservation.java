import java.util.Date;  // Requirement 21: Java library class
import java.text.SimpleDateFormat; // Requirement 21: Java library class

public class Reservation {
    private int reservationId;  // Requirement 2: Variables
    private Room room; // Requirement 2: Variables
    private Customer customer; // Requirement 2: Variables
    private Date startDate; // Requirement 2: Variables
    private Date endDate; // Requirement 2: Variables
    private double totalRate; // Requirement 2: Variables

    public Reservation(int reservationId, Room room, Customer customer, Date startDate, Date endDate, double totalRate) {  // Requirement 16: Constructor
        this.reservationId = reservationId;
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalRate = totalRate;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getTotalRate() {
        return totalRate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = dateFormat.format(startDate);
        String formattedEndDate = dateFormat.format(endDate);
        return "Reservation ID: " + reservationId + ", Room: " + room.getRoomNumber() + ", Customer: " + customer.getName() +
                " (Email: " + customer.getEmail() + ", Phone: " + customer.getPhoneNumber() + "), Dates: " + formattedStartDate + " to " + formattedEndDate +
                ", Total Rate: $" + getTotalRate();
    }
}