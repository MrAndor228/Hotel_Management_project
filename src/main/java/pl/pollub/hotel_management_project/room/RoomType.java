package pl.pollub.hotel_management_project.room;

public enum RoomType {
    SINGLE(1),
    DOUBLE(2),
    SUITE(6);

    private final int maxCapacity;

    RoomType(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public boolean allowsCapacity(int capacity) {
        return capacity >= 1 && capacity <= maxCapacity;
    }
}
