public class Room {
    private int roomNumber; // Requirement 2: Variables

    private String type; // Requirement 2: Variables

    private double rate; // Requirement 2: Variables

    private boolean isAvailable; // Requirement 2: Variables

    public Room(int roomNumber, String type, double rate){  // Requirement 16: Constructor
        this.roomNumber = roomNumber;
        this.type = type;
        this.rate = rate;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getRate() {
        return rate;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void setAvailable(boolean available){
        isAvailable = available;
    }  // Requirement 23: this keyword

    @Override
    public String toString () {
        return "Room Number: " + roomNumber + ", Type: " + type + ", Rate: " + rate + ", Avaliable" + isAvailable;
    }

}
