package Utils;

public class PoeInventory {

    public static Coordinates getFirstCase() {
        return new Coordinates(4290, 820);
    }

    public static Coordinates getLastCase() {
        return new Coordinates(
                getFirstCase().getX() + 11 * getSizeInBetween(),
                getFirstCase().getY() + 4 * getSizeInBetween()
        );
    }

    public static int getSizeInBetween() {
        return 70;
    }

}
