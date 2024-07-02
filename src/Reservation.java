import java.util.Date;
import java.text.SimpleDateFormat;

public class Reservation {
    private int reservationId;
    private Room room;
    private Customer customer;
    private Date startDate;
    private Date endDate;
    private double totalRate;

    public Reservation(int reservationId, Room room, Customer customer, Date startDate, Date endDate, double totalRate) {
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