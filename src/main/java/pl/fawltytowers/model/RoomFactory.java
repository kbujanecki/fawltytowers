package pl.fawltytowers.model;

public class RoomFactory {
	public static StandardRoom createStandardRoom(Hotel hotel, WindowView windowView) {
		StandardRoom room = new StandardRoom();
		setRoomProp(hotel, windowView, room);
		return room;
	}

	public static Room createSpecialRoom(Hotel hotel, WindowView windowView) {
		Room room = new SpecialRoom();
		setRoomProp(hotel, windowView, room);
		return room;
	}

	public static Room createExclusiveRoom(Hotel hotel, WindowView windowView) {
		Room room = new ExclusiveRoom();
		setRoomProp(hotel, windowView, room);
		return room;
	}

	private static void setRoomProp(Hotel hotel, WindowView windowView, Room room) {
		room.setHotel(hotel);
		room.setWindowView(windowView);
	}

}
