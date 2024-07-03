import java.util.ArrayList;
import java.util.List;

public class RoomList<T> {
    private List<T> rooms = new ArrayList<>();

    public void addRoom(T room) {
        rooms.add(room);
    }

    public List<T> getRooms() {
        return rooms;
    }

    public T findAvailableRoom(String type) {
        for (T room : rooms) {
            if (room instanceof Room) {
                Room r = (Room) room;
                if (r.getType().equalsIgnoreCase(type) && r.isAvailable()) {
                    return room;
                }
            }
        }
        return null;
    }
}
