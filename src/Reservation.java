import java.util.Date;
import java.text.SimpleDateFormat;

public class Reservation {
    private int reservationId;
    private Room room;
    private Customer customer;
    private Date reservationDate;

    public Reservation(int reservationId, Room room, Customer customer, Date reservationDate) {
        this.reservationId = reservationId;
        this.room = room;
        this.customer = customer;
        this.reservationDate = reservationDate;
    }

    public Room getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    @Override
    public String toString() {
        SimpleDateFromat  = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(reservationDate);
        return "Reservation ID: " + reservationId + ", Room: " + room.getRoomNumber() + ", Customer: " + customer.getName() +
                " (Email: " + customer.getEmail() + ", Phone: " + customer.getPhoneNumber() + "), Date: " + formattedDate;
    }
}