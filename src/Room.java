public class Room {
    private int roomNumber;

    private String type;

    private double rate;

    private boolean isAvailable;

    public Room(int roomNumber, String type, double rate){
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
    }

    @Override
    public String toString () {
        return "Room Number: " + roomNumber + ", Type: " + type + ", Rate: " + rate + ", Avaliable";
    }

}
